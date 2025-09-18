package com.sinchana.credit_card_management_service.service.impl;

import com.sinchana.credit_card_management_service.entity.CardApplication;
import com.sinchana.credit_card_management_service.exception.ResourceNotFoundException;
import com.sinchana.credit_card_management_service.repository.CardApplicationRepository;
import com.sinchana.credit_card_management_service.service.CardApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CardApplicationServiceImpl implements CardApplicationService {

    private final CardApplicationRepository cardApplicationRepository;

    public CardApplicationServiceImpl(CardApplicationRepository cardApplicationRepository) {
        this.cardApplicationRepository = cardApplicationRepository;
    }

    @Override
    public CardApplication applyForCard(CardApplication application) {
        // Default status is "PENDING"
        application.setStatus("PENDING");
        application.setApplicationDate(java.time.LocalDateTime.now());
        return cardApplicationRepository.save(application);
    }

    @Override
    public List<CardApplication> getApplicationsByUserId(UUID userId) {
        return cardApplicationRepository.findByUserId(userId);
    }
}
