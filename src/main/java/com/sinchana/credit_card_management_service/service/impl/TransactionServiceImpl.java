package com.sinchana.credit_card_management_service.service.impl;

import com.sinchana.credit_card_management_service.entity.Transaction;
import com.sinchana.credit_card_management_service.repository.TransactionRepository;
import com.sinchana.credit_card_management_service.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction simulateTransaction(Transaction transaction) {
        transaction.setTransactionDate(java.time.LocalDateTime.now());
        transaction.setPaymentStatus("SUCCESS"); // default status
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByCardId(UUID cardId) {
        return transactionRepository.findByCardId(cardId);
    }
}
