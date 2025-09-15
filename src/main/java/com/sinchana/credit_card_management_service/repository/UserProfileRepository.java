package com.sinchana.credit_card_management_service.repository;

import com.sinchana.credit_card_management_service.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    Optional<UserProfile> findByUserId(UUID userId);
}
