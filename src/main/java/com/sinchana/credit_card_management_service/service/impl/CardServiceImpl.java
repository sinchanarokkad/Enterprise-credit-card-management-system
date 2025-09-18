package com.sinchana.credit_card_management_service.service.impl;

import com.sinchana.credit_card_management_service.entity.Card;
import com.sinchana.credit_card_management_service.repository.CardRepository;
import com.sinchana.credit_card_management_service.service.CardService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Optional<Card> getCardById(UUID cardId) {
        return cardRepository.findById(cardId);
    }

    @Override
    public List<Card> getCardsByUserId(UUID userId) {
        return cardRepository.findByUserId(userId);
    }

    @Override
    public Card updateCardStatus(UUID cardId, String status) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        card.setStatus(status);
        return cardRepository.save(card);
    }

    @Override
    public Card updateCardLimit(UUID cardId, double newLimit) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        card.setCreditLimit(newLimit);
        card.setAvailableLimit(newLimit); // optional logic
        return cardRepository.save(card);
    }
}
