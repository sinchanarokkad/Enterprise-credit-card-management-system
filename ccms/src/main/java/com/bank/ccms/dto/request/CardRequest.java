package com.bank.ccms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CardRequest {
    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotBlank(message = "Card type is required")
    private String cardType;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Credit limit is required")
    private Double creditLimit;

    private Double availableLimit;
    private LocalDate expiryDate;

    @NotNull(message = "User ID is required")
    private UUID userId;
}
