package com.sinchana.credit_card_management_service.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for creating or updating a Card.
 */
public class CardRequestDTO {

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotBlank(message = "Card type is required")
    private String cardType;  // e.g., VISA, MASTERCARD, AMEX

    @Positive(message = "Credit limit must be positive")
    private Double creditLimit;

    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;

    // Getters and Setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
