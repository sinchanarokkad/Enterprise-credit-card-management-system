package com.bank.ccms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class TransactionRequest {
    @NotNull(message = "Card ID is required")
    private UUID cardId;

    @NotBlank(message = "Merchant name is required")
    private String merchantName;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Transaction type is required")
    private String transactionType;

    @NotBlank(message = "Payment status is required")
    private String paymentStatus;
}
