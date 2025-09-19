package com.sinchana.credit_card_management_service.controller;

import com.sinchana.credit_card_management_service.entity.Transaction;
import com.sinchana.credit_card_management_service.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Simulate a new transaction
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.simulateTransaction(transaction);
        return ResponseEntity.ok(createdTransaction);
    }

    // Fetch transaction history for a card
    @GetMapping("/{cardId}")
    public ResponseEntity<List<Transaction>> getTransactionsByCard(@PathVariable UUID cardId) {
        List<Transaction> transactions = transactionService.getTransactionsByCardId(cardId);
        return ResponseEntity.ok(transactions);
    }
}
