package com.sinchana.credit_card_management_service.service;

import com.sinchana.credit_card_management_service.dto.request.UserRequestDTO;
import com.sinchana.credit_card_management_service.dto.response.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO request);
    UserResponseDTO getUserById(UUID id);
    Page<UserResponseDTO> getAllUsers(Pageable pageable);
    UserResponseDTO updateUser(UUID id, UserRequestDTO request);
    void deleteUser(UUID id);
}