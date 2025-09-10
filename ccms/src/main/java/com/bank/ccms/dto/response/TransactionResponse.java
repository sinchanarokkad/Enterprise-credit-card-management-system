package com.bank.ccms.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionResponse {
    private UUID id;
    private UUID cardId;
    private String merchantName;
    private Double amount;
    private LocalDateTime transactionDate;
    private String category;
    private String transactionType;
    private String paymentStatus;
}
