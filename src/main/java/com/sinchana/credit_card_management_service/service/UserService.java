package com.sinchana.credit_card_management_service.service;

import com.sinchana.credit_card_management_service.dto.request.UserRequestDTO;
import com.sinchana.credit_card_management_service.dto.response.UserResponseDTO;

import java.util.UUID;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO request);
    UserResponseDTO getUserById(UUID id);
    void deleteUser(UUID id);
}
