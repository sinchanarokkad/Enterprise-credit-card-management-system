package com.sinchana.credit_card_management_service.service.impl;

import com.sinchana.credit_card_management_service.dto.request.UserRequestDTO;
import com.sinchana.credit_card_management_service.dto.response.UserResponseDTO;
import com.sinchana.credit_card_management_service.entity.User;
import com.sinchana.credit_card_management_service.repository.UserRepository;
import com.sinchana.credit_card_management_service.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        return response;
    }

    @Override
    public UserResponseDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setUserType(user.getUserType());

        return response;
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
