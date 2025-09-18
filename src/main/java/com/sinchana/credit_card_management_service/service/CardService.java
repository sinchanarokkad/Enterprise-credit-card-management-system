package com.sinchana.credit_card_management_service.service;

import com.sinchana.credit_card_management_service.entity.Card;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardService {
    Card createCard(Card card);
    Optional<Card> getCardById(UUID cardId);
    List<Card> getCardsByUserId(UUID userId);
    Card updateCardStatus(UUID cardId, String status);
    Card updateCardLimit(UUID cardId, double newLimit);
}
