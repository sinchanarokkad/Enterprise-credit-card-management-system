package com.sinchana.credit_card_management_service.controller;

import com.sinchana.credit_card_management_service.entity.Card;
import com.sinchana.credit_card_management_service.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private static final Logger logger = LoggerFactory.getLogger(CardController.class);
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Card>> getCardsByUser(@PathVariable UUID userId) {
        logger.info("Fetching cards for user id: {}", userId);
        List<Card> cards = cardService.getCardsByUserId(userId);
        logger.info("Found {} cards for user id: {}", cards.size(), userId);
        return ResponseEntity.ok(cards);
    }

    @PutMapping("/{cardId}/status")
    public ResponseEntity<Card> updateCardStatus(@PathVariable UUID cardId,
                                                 @RequestParam String status) {
        logger.info("Updating status of card {} to {}", cardId, status);
        Card updatedCard = cardService.updateCardStatus(cardId, status);
        logger.info("Updated card status for card {}", cardId);
        return ResponseEntity.ok(updatedCard);
    }

    @PutMapping("/{cardId}/limit")
    public ResponseEntity<Card> updateCardLimit(@PathVariable UUID cardId,
                                                @RequestParam double newLimit) {
        logger.info("Updating credit limit of card {} to {}", cardId, newLimit);
        Card updatedCard = cardService.updateCardLimit(cardId, newLimit);
        logger.info("Updated credit limit for card {}", cardId);
        return ResponseEntity.ok(updatedCard);
    }
}
