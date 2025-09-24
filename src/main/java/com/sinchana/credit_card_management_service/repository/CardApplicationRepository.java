package com.sinchana.credit_card_management_service.repository;

import com.sinchana.credit_card_management_service.entity.CardApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CardApplicationRepository extends JpaRepository<CardApplication, UUID> {
    List<CardApplication> findByUser_Id(UUID userId);
}
