package com.sinchana.credit_card_management_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

/**
 * DTO for creating a new transaction request.
 */
public class TransactionRequestDTO {

    @NotNull(message = "Card ID is required")
    private UUID cardId;

    @NotBlank(message = "Merchant name is required")
    private String merchantName;

    @Positive(message = "Amount must be positive")
    private Double amount;

    private String category;        // e.g., FOOD, TRAVEL, SHOPPING
    private String transactionType; // e.g., PURCHASE, PAYMENT, REFUND
    private boolean bnpl;           // Buy Now Pay Later flag

    // Getters and Setters
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

    public boolean isBnpl() {
        return bnpl;
    }

    public void setBnpl(boolean bnpl) {
        this.bnpl = bnpl;
    }
}
