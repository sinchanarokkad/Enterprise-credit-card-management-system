package com.sinchana.credit_card_management_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(nullable = false)
    private String merchantName;

    private double amount;

    private LocalDateTime transactionDate;

    private String category; // "FOOD", "TRAVEL", "UTILITY", "ENTERTAINMENT", "OTHER"

    private String transactionType; // "DEBIT", "CREDIT"

    private String paymentStatus; // "SUCCESS", "FAILED", "PENDING"

    // Constructors
    public Transaction() {}

    public Transaction(Card card, String merchantName, double amount, LocalDateTime transactionDate, String category, String transactionType, String paymentStatus) {
        this.card = card;
        this.merchantName = merchantName;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.category = category;
        this.transactionType = transactionType;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Card getCard() { return card; }
    public void setCard(Card card) { this.card = card; }

    public String getMerchantName() { return merchantName; }
    public void setMerchantName(String merchantName) { this.merchantName = merchantName; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public UUID getCardId() {
        return card != null ? card.getId() : null;
    }

}
