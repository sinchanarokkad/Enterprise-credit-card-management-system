package com.sinchana.credit_card_management_service.controller;

import com.sinchana.credit_card_management_service.entity.UserProfile;
import com.sinchana.credit_card_management_service.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable UUID userId) {
        logger.info("Fetching profile for user {}", userId);
        return userProfileService.getProfileByUserId(userId)
                .map(profile -> {
                    logger.info("Profile found for user {}", userId);
                    return ResponseEntity.ok(profile);
                })
                .orElseGet(() -> {
                    logger.warn("Profile not found for user {}", userId);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<UserProfile> createOrUpdateProfile(@RequestBody UserProfile profile) {
        logger.info("Creating/updating profile for user {}", profile.getUserId());
        UserProfile savedProfile = userProfileService.createOrUpdateProfile(profile);
        logger.info("Profile saved for user {}", profile.getUserId());
        return ResponseEntity.ok(savedProfile);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable UUID userId) {
        logger.info("Deleting profile for user {}", userId);
        userProfileService.deleteProfile(userId);
        logger.info("Profile deleted for user {}", userId);
        return ResponseEntity.noContent().build();
    }
}
