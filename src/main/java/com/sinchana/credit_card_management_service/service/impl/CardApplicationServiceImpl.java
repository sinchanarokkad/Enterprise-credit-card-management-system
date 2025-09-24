package com.sinchana.credit_card_management_service.service.impl;

import com.sinchana.credit_card_management_service.entity.CardApplication;
import com.sinchana.credit_card_management_service.repository.CardApplicationRepository;
import com.sinchana.credit_card_management_service.repository.UserRepository;
import com.sinchana.credit_card_management_service.service.CardApplicationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CardApplicationServiceImpl implements CardApplicationService {

    private final CardApplicationRepository cardApplicationRepository;
    private final UserRepository userRepository;

    public CardApplicationServiceImpl(CardApplicationRepository cardApplicationRepository,
                                      UserRepository userRepository) {
        this.cardApplicationRepository = cardApplicationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CardApplication applyForCard(CardApplication application, UUID userId) {
        application.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
        application.setStatus("APPLIED");
        application.setApplicationDate(LocalDateTime.now());
        return cardApplicationRepository.save(application);
    }

    @Override
    public List<CardApplication> getApplicationsByUserId(UUID userId) {
        return cardApplicationRepository.findByUser_Id(userId);
    }
}
