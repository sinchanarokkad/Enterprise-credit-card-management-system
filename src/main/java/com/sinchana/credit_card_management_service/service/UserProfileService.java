package com.sinchana.credit_card_management_service.service;

import com.sinchana.credit_card_management_service.entity.UserProfile;
import java.util.Optional;
import java.util.UUID;

public interface UserProfileService {
    Optional<UserProfile> getProfileByUserId(UUID userId);
    UserProfile createOrUpdateProfile(UserProfile profile);
    void deleteProfile(UUID profileId);
}
