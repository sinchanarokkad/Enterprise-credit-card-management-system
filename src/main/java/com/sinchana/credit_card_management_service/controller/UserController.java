package com.sinchana.credit_card_management_service.controller;

import com.sinchana.credit_card_management_service.entity.User;
import com.sinchana.credit_card_management_service.entity.UserProfile;
import com.sinchana.credit_card_management_service.service.UserService;
import com.sinchana.credit_card_management_service.service.UserProfileService;
import com.sinchana.credit_card_management_service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserProfileService userProfileService;

    public UserController(UserService userService, UserProfileService userProfileService) {
        this.userService = userService;
        this.userProfileService = userProfileService;
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // Get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return ResponseEntity.ok(user);
    }

    // Get user profile by userId
    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable UUID userId) {
        UserProfile profile = userProfileService.getProfileByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for user id: " + userId));
        return ResponseEntity.ok(profile);
    }

    // Create or update user profile
    @PutMapping("/{userId}/profile")
    public ResponseEntity<UserProfile> createOrUpdateProfile(@PathVariable UUID userId,
                                                             @RequestBody UserProfile profileRequest) {
        profileRequest.setUserId(userId);
        UserProfile updatedProfile = userProfileService.createOrUpdateProfile(profileRequest);
        return ResponseEntity.ok(updatedProfile);
    }

    // Delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
