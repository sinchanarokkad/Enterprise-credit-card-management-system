package com.sinchana.credit_card_management_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinchana.credit_card_management_service.dto.request.UserRequestDTO;
import com.sinchana.credit_card_management_service.dto.response.UserResponseDTO;
import com.sinchana.credit_card_management_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = UserController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration.class,
                org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class,
                org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
                org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                        com.sinchana.credit_card_management_service.config.KafkaConfig.class,
                        com.sinchana.credit_card_management_service.config.RedisConfig.class,
                        com.sinchana.credit_card_management_service.config.MetricsConfig.class
                })
        }
)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private com.sinchana.credit_card_management_service.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private com.sinchana.credit_card_management_service.security.JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRequestDTO testUserRequest;
    private UserResponseDTO testUserResponse;
    private UUID testUserId;

    @BeforeEach
    void setUp() {
        testUserId = UUID.randomUUID();

        testUserRequest = new UserRequestDTO();
        testUserRequest.setEmail("test@example.com");
        testUserRequest.setPassword("password123");
        testUserRequest.setUserType("CUSTOMER");

        testUserResponse = new UserResponseDTO();
        testUserResponse.setId(testUserId);
        testUserResponse.setEmail("test@example.com");
        testUserResponse.setUserType("CUSTOMER");
    }

    @Test
    void createUser_ShouldReturnCreatedUser_WhenValidRequest() throws Exception {
        // Given
        when(userService.createUser(any(UserRequestDTO.class))).thenReturn(testUserResponse);

        // When & Then
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUserRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testUserId.toString()))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.userType").value("CUSTOMER"));
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() throws Exception {
        // Given
        when(userService.getUserById(testUserId)).thenReturn(testUserResponse);

        // When & Then
        mockMvc.perform(get("/api/users/{id}", testUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testUserId.toString()))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.userType").value("CUSTOMER"));
    }

    @Test
    void deleteUser_ShouldReturnNoContent_WhenUserExists() throws Exception {
        // Given
        when(userService.getUserById(testUserId)).thenReturn(testUserResponse);

        // When & Then
        mockMvc.perform(delete("/api/users/{id}", testUserId))
                .andExpect(status().isNoContent());
    }

    @Test
    void createUser_ShouldReturnBadRequest_WhenInvalidRequest() throws Exception {
        // Given
        UserRequestDTO invalidRequest = new UserRequestDTO();
        // Missing required fields

        // When & Then
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}


