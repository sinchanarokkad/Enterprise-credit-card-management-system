package com.sinchana.credit_card_management_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

/**
 * DTO for submitting a new Credit Card Application.
 */
public class CardApplicationRequestDTO {

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotBlank(message = "Card type is required")
    private String cardType;  // e.g., VISA, MASTERCARD, AMEX, RUPAY

    @Positive(message = "Requested limit must be positive")
    private Double requestedLimit;

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

    public Double getRequestedLimit() {
        return requestedLimit;
    }

    public void setRequestedLimit(Double requestedLimit) {
        this.requestedLimit = requestedLimit;
    }
}
