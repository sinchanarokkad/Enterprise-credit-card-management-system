package com.sinchana.credit_card_management_service.service;

import com.sinchana.credit_card_management_service.entity.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    Transaction simulateTransaction(Transaction transaction);

    List<Transaction> getTransactionsByCardId(UUID cardId);
}
