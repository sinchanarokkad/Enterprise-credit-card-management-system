package com.sinchana.credit_card_management_service.service.impl;

import com.sinchana.credit_card_management_service.entity.UserProfile;
import com.sinchana.credit_card_management_service.repository.UserProfileRepository;
import com.sinchana.credit_card_management_service.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    private final UserProfileRepository profileRepository;

    public UserProfileServiceImpl(UserProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<UserProfile> getProfileByUserId(UUID userId) {
        logger.info("Fetching user profile for userId={}", userId);
        return profileRepository.findByUserId(userId);
    }

    @Override
    public UserProfile createOrUpdateProfile(UserProfile profile) {
        logger.info("Creating/updating profile for userId={}", profile.getUserId());
        UserProfile savedProfile = profileRepository.save(profile);
        logger.info("Profile saved successfully with id={}", savedProfile.getId());
        return savedProfile;
    }

    @Override
    public void deleteProfile(UUID profileId) {
        logger.info("Deleting profile with id={}", profileId);
        profileRepository.deleteById(profileId);
        logger.info("Profile deleted successfully");
    }
}
