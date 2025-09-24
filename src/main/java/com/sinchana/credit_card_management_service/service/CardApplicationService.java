package com.sinchana.credit_card_management_service.service;

import com.sinchana.credit_card_management_service.entity.CardApplication;

import java.util.List;
import java.util.UUID;

public interface CardApplicationService {

    // Apply for a card (takes application object + userId)
    CardApplication applyForCard(CardApplication application, UUID userId);

    // Fetch applications for a given user
    List<CardApplication> getApplicationsByUserId(UUID userId);
}
