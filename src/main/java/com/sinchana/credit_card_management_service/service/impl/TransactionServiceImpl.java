package com.sinchana.credit_card_management_service.service.impl;

import com.sinchana.credit_card_management_service.entity.Transaction;
import com.sinchana.credit_card_management_service.repository.TransactionRepository;
import com.sinchana.credit_card_management_service.service.KafkaEventPublisher;
import com.sinchana.credit_card_management_service.service.RedisService;
import com.sinchana.credit_card_management_service.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionRepository transactionRepository;
    private final RedisService redisService;
    private final KafkaEventPublisher kafkaEventPublisher;

    public TransactionServiceImpl(TransactionRepository transactionRepository, 
                                RedisService redisService, 
                                KafkaEventPublisher kafkaEventPublisher) {
        this.transactionRepository = transactionRepository;
        this.redisService = redisService;
        this.kafkaEventPublisher = kafkaEventPublisher;
    }

    @Override
    public Transaction simulateTransaction(Transaction transaction) {
        logger.info("Simulating transaction for cardId={} amount={}", transaction.getCardId(), transaction.getAmount());
        transaction.setTransactionDate(java.time.LocalDateTime.now());
        transaction.setPaymentStatus("SUCCESS");
        Transaction savedTransaction = transactionRepository.save(transaction);
        logger.info("Transaction saved with id={}", savedTransaction.getId());
        
        // Cache transaction data
        redisService.set("transaction:" + savedTransaction.getId().toString(), savedTransaction, 5, java.util.concurrent.TimeUnit.MINUTES);
        
        // Publish transaction event
        kafkaEventPublisher.publishTransactionEvent(
            savedTransaction.getCard().getUser().getId().toString(), 
            savedTransaction.getCardId().toString(), 
            savedTransaction
        );
        
        return savedTransaction;
    }

    @Override
    @Cacheable(value = "transactions", key = "#cardId")
    public List<Transaction> getTransactionsByCardId(UUID cardId) {
        logger.info("Fetching transactions for cardId={}", cardId);
        
        // Try to get from cache first
        @SuppressWarnings("unchecked")
        List<Transaction> cached = redisService.get("transactions:card:" + cardId.toString(), List.class);
        if (cached != null) {
            logger.info("Returning cached transactions for cardId={}", cardId);
            return cached;
        }
        
        List<Transaction> transactions = transactionRepository.findByCard_Id(cardId);
        
        // Cache the results
        redisService.set("transactions:card:" + cardId.toString(), transactions, 5, java.util.concurrent.TimeUnit.MINUTES);
        
        return transactions;
    }
}
