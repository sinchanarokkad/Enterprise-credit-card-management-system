package com.sinchana.credit_card_management_service.service.impl;

import com.sinchana.credit_card_management_service.entity.Transaction;
import com.sinchana.credit_card_management_service.repository.TransactionRepository;
import com.sinchana.credit_card_management_service.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction simulateTransaction(Transaction transaction) {
        logger.info("Simulating transaction for cardId={} amount={}", transaction.getCardId(), transaction.getAmount());
        transaction.setTransactionDate(java.time.LocalDateTime.now());
        transaction.setPaymentStatus("SUCCESS");
        Transaction savedTransaction = transactionRepository.save(transaction);
        logger.info("Transaction saved with id={}", savedTransaction.getId());
        return savedTransaction;
    }

    @Override
    public List<Transaction> getTransactionsByCardId(UUID cardId) {
        logger.info("Fetching transactions for cardId={}", cardId);
        return transactionRepository.findByCard_Id(cardId);
    }
}
