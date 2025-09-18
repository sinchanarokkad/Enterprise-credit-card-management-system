package com.sinchana.credit_card_management_service.service;

import com.sinchana.credit_card_management_service.entity.CardApplication;
import java.util.List;
import java.util.UUID;

public interface CardApplicationService {

    CardApplication applyForCard(CardApplication application);

    List<CardApplication> getApplicationsByUserId(UUID userId);
}
