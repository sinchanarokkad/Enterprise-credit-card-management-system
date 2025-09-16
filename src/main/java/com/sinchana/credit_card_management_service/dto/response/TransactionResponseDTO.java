package com.sinchana.credit_card_management_service.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for returning transaction details after processing.
 */
public class TransactionResponseDTO {

    private UUID id;
    private UUID cardId;
    private String merchantName;
    private Double amount;
    private LocalDateTime transactionDate;
    private String category;
    private String transactionType;
    private String paymentStatus; // SUCCESS, FAILED, PENDING
    private boolean bnpl;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCardId() {
        return cardId;
    }

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public boolean isBnpl() {
        return bnpl;
    }

    public void setBnpl(boolean bnpl) {
        this.bnpl = bnpl;
    }
}
