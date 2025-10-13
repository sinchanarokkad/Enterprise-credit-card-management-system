package com.sinchana.credit_card_management_service.service.impl;

import com.sinchana.credit_card_management_service.dto.request.UserRequestDTO;
import com.sinchana.credit_card_management_service.dto.response.UserResponseDTO;
import com.sinchana.credit_card_management_service.entity.User;
import com.sinchana.credit_card_management_service.repository.UserRepository;
import com.sinchana.credit_card_management_service.service.KafkaEventPublisher;
import com.sinchana.credit_card_management_service.service.RedisService;
import com.sinchana.credit_card_management_service.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final KafkaEventPublisher kafkaEventPublisher;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           RedisService redisService, KafkaEventPublisher kafkaEventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
        this.kafkaEventPublisher = kafkaEventPublisher;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserType(request.getUserType());

        User saved = userRepository.save(user);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(saved.getId());
        response.setEmail(saved.getEmail());
        response.setUserType(saved.getUserType());

        // Cache the user data
        redisService.set("user:" + saved.getId().toString(), response, 10, java.util.concurrent.TimeUnit.MINUTES);

        // Publish user creation event
        kafkaEventPublisher.publishUserProfileEvent(saved.getId().toString(), response);

        return response;
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public UserResponseDTO getUserById(UUID id) {
        // Try to get from cache first
        UserResponseDTO cached = redisService.get("user:" + id.toString(), UserResponseDTO.class);
        if (cached != null) {
            return cached;
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setUserType(user.getUserType());

        // Cache the response
        redisService.set("user:" + id.toString(), response, 10, java.util.concurrent.TimeUnit.MINUTES);

        return response;
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(user -> {
            UserResponseDTO response = new UserResponseDTO();
            response.setId(user.getId());
            response.setEmail(user.getEmail());
            response.setUserType(user.getUserType());
            return response;
        });
    }

    @Override
    public UserResponseDTO updateUser(UUID id, UserRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setUserType(request.getUserType());

        User saved = userRepository.save(user);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(saved.getId());
        response.setEmail(saved.getEmail());
        response.setUserType(saved.getUserType());

        // Update cache
        redisService.set("user:" + id.toString(), response, 10, java.util.concurrent.TimeUnit.MINUTES);

        return response;
    }

    @Override
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);

        // Remove from Redis cache
        redisService.delete("user:" + id.toString());

        // Publish user deletion event
        kafkaEventPublisher.publishUserProfileEvent(id.toString(), "USER_DELETED");
    }
}