package com.sinchana.credit_card_management_service.controller;

import com.sinchana.credit_card_management_service.entity.Transaction;
import com.sinchana.credit_card_management_service.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Simulate a new transaction
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        logger.info("Creating transaction for card {} of amount {}",
                transaction.getCard().getId(), transaction.getAmount());

        Transaction createdTransaction = transactionService.simulateTransaction(transaction);

        logger.info("Transaction {} created successfully", createdTransaction.getId());
        return ResponseEntity.ok(createdTransaction);
    }

    // Fetch transaction history for a card
    @GetMapping("/{cardId}")
    public ResponseEntity<List<Transaction>> getTransactionsByCard(@PathVariable UUID cardId) {
        logger.info("Fetching transactions for card {}", cardId);

        List<Transaction> transactions = transactionService.getTransactionsByCardId(cardId);

        logger.info("{} transactions found for card {}", transactions.size(), cardId);
        return ResponseEntity.ok(transactions);
    }
}
