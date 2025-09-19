package com.sinchana.credit_card_management_service.controller;

import com.sinchana.credit_card_management_service.entity.CardApplication;
import com.sinchana.credit_card_management_service.service.CardApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cards/applications")
public class CardApplicationController {

    private final CardApplicationService cardApplicationService;

    public CardApplicationController(CardApplicationService cardApplicationService) {
        this.cardApplicationService = cardApplicationService;
    }

    // Submit new card application
    @PostMapping
    public ResponseEntity<CardApplication> applyForCard(@RequestBody CardApplication application) {
        CardApplication createdApp = cardApplicationService.applyForCard(application);
        return ResponseEntity.ok(createdApp);
    }

    // Get all applications of a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<CardApplication>> getApplicationsByUser(@PathVariable UUID userId) {
        List<CardApplication> applications = cardApplicationService.getApplicationsByUserId(userId);
        return ResponseEntity.ok(applications);
    }
}
