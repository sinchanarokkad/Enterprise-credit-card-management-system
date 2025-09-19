package com.sinchana.credit_card_management_service.controller;

import com.sinchana.credit_card_management_service.entity.UserProfile;
import com.sinchana.credit_card_management_service.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    // Get profile by userId
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable UUID userId) {
        return userProfileService.getProfileByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create or update profile
    @PostMapping
    public ResponseEntity<UserProfile> createOrUpdateProfile(@RequestBody UserProfile profile) {
        UserProfile savedProfile = userProfileService.createOrUpdateProfile(profile);
        return ResponseEntity.ok(savedProfile);
    }

    // Delete profile
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable UUID userId) {
        userProfileService.deleteProfile(userId);
        return ResponseEntity.noContent().build();
    }
}
