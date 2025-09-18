package com.sinchana.credit_card_management_service.service;

import com.sinchana.credit_card_management_service.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User createUser(User user);

    Optional<User> getUserById(UUID userId);

    void deleteUser(UUID userId);  // Add this method
}
