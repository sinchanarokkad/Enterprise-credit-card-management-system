package com.sinchana.credit_card_management_service.repository;

import com.sinchana.credit_card_management_service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByCard_Id(UUID cardId);
}
