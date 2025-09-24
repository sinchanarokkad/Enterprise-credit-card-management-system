package com.sinchana.credit_card_management_service.service.impl;

import com.sinchana.credit_card_management_service.entity.Card;
import com.sinchana.credit_card_management_service.repository.CardRepository;
import com.sinchana.credit_card_management_service.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardServiceImpl implements CardService {

    private static final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card createCard(Card card) {
        logger.info("Creating card for userId={}", card.getUserId());
        Card savedCard = cardRepository.save(card);
        logger.info("Card created with id={}", savedCard.getId());
        return savedCard;
    }

    @Override
    public Optional<Card> getCardById(UUID cardId) {
        logger.info("Fetching card by id={}", cardId);
        return cardRepository.findById(cardId);
    }

    @Override
    public List<Card> getCardsByUserId(UUID userId) {
        logger.info("Fetching cards for userId={}", userId);
        return cardRepository.findByUser_Id(userId); // ✅ correct
    }


    @Override
    public Card updateCardStatus(UUID cardId, String status) {
        logger.info("Updating status for cardId={} to {}", cardId, status);
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        card.setStatus(status);
        Card updatedCard = cardRepository.save(card);
        logger.info("Card status updated successfully for cardId={}", cardId);
        return updatedCard;
    }

    @Override
    public Card updateCardLimit(UUID cardId, double newLimit) {
        logger.info("Updating credit limit for cardId={} to {}", cardId, newLimit);
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        card.setCreditLimit(newLimit);
        card.setAvailableLimit(newLimit);
        Card updatedCard = cardRepository.save(card);
        logger.info("Card limit updated successfully for cardId={}", cardId);
        return updatedCard;
    }
}
