package com.sinchana.credit_card_management_service.controller;

import com.sinchana.credit_card_management_service.entity.Card;
import com.sinchana.credit_card_management_service.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // Get all cards for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<Card>> getCardsByUser(@PathVariable UUID userId) {
        List<Card> cards = cardService.getCardsByUserId(userId);
        return ResponseEntity.ok(cards);
    }

    // Update card status (ACTIVE / BLOCKED)
    @PutMapping("/{cardId}/status")
    public ResponseEntity<Card> updateCardStatus(@PathVariable UUID cardId,
                                                 @RequestParam String status) {
        Card updatedCard = cardService.updateCardStatus(cardId, status);
        return ResponseEntity.ok(updatedCard);
    }

    // Update card limit
    @PutMapping("/{cardId}/limit")
    public ResponseEntity<Card> updateCardLimit(@PathVariable UUID cardId,
                                                @RequestParam double newLimit) {
        Card updatedCard = cardService.updateCardLimit(cardId, newLimit);
        return ResponseEntity.ok(updatedCard);
    }
}
