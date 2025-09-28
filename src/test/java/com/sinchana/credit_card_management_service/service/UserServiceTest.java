package com.sinchana.credit_card_management_service.service;

import com.sinchana.credit_card_management_service.dto.request.UserRequestDTO;
import com.sinchana.credit_card_management_service.dto.response.UserResponseDTO;
import com.sinchana.credit_card_management_service.entity.User;
import com.sinchana.credit_card_management_service.exception.ResourceNotFoundException;
import com.sinchana.credit_card_management_service.mapper.UserMapper;
import com.sinchana.credit_card_management_service.repository.UserRepository;
import com.sinchana.credit_card_management_service.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RedisService redisService;

    @Mock
    private KafkaEventPublisher kafkaEventPublisher;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserRequestDTO testUserRequest;
    private UserResponseDTO testUserResponse;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");
        testUser.setFirstName("John");
        testUser.setLastName("Doe");

        testUserRequest = new UserRequestDTO();
        testUserRequest.setEmail("test@example.com");
        testUserRequest.setPassword("password123");
        testUserRequest.setFirstName("John");
        testUserRequest.setLastName("Doe");

        testUserResponse = new UserResponseDTO();
        testUserResponse.setId(testUser.getId());
        testUserResponse.setEmail("test@example.com");
        testUserResponse.setFirstName("John");
        testUserResponse.setLastName("Doe");
    }

    @Test
    void createUser_ShouldReturnUserResponseDTO_WhenValidRequest() {
        // Given
        when(userMapper.toEntity(testUserRequest)).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toResponseDTO(testUser)).thenReturn(testUserResponse);

        // When
        UserResponseDTO result = userService.createUser(testUserRequest);

        // Then
        assertNotNull(result);
        assertEquals(testUserResponse.getId(), result.getId());
        assertEquals(testUserResponse.getEmail(), result.getEmail());
        assertEquals(testUserResponse.getFirstName(), result.getFirstName());
        assertEquals(testUserResponse.getLastName(), result.getLastName());

        verify(userRepository).save(testUser);
        verify(userMapper).toEntity(testUserRequest);
        verify(userMapper).toResponseDTO(testUser);
        verify(kafkaEventPublisher).publishUserProfileEvent(eq(testUser.getId().toString()), any());
    }

    @Test
    void getUserById_ShouldReturnUserResponseDTO_WhenUserExists() {
        // Given
        UUID userId = testUser.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(userMapper.toResponseDTO(testUser)).thenReturn(testUserResponse);

        // When
        UserResponseDTO result = userService.getUserById(userId);

        // Then
        assertNotNull(result);
        assertEquals(testUserResponse.getId(), result.getId());
        assertEquals(testUserResponse.getEmail(), result.getEmail());

        verify(userRepository).findById(userId);
        verify(userMapper).toResponseDTO(testUser);
    }

    @Test
    void getUserById_ShouldThrowResourceNotFoundException_WhenUserNotFound() {
        // Given
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository).findById(userId);
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() {
        // Given
        UUID userId = testUser.getId();
        when(userRepository.existsById(userId)).thenReturn(true);

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository).existsById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteUser_ShouldThrowResourceNotFoundException_WhenUserNotFound() {
        // Given
        UUID userId = UUID.randomUUID();
        when(userRepository.existsById(userId)).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(userId));
        verify(userRepository).existsById(userId);
        verify(userRepository, never()).deleteById(userId);
    }
}
