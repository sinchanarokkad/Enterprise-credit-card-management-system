package com.sinchana.credit_card_management_service.service.impl;

import com.sinchana.credit_card_management_service.entity.UserProfile;
import com.sinchana.credit_card_management_service.repository.UserProfileRepository;
import com.sinchana.credit_card_management_service.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository profileRepository;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<UserProfile> getProfileByUserId(UUID userId) {
        return profileRepository.findByUserId(userId);
    }

    @Override
    public UserProfile createOrUpdateProfile(UserProfile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public void deleteProfile(UUID profileId) {
        profileRepository.deleteById(profileId);
    }
}
