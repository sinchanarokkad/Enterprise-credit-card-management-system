package com.bank.ccms.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CardResponse {
    private UUID id;
    private String cardNumber;
    private String cardType;
    private String status;
    private Double creditLimit;
    private Double availableLimit;
    private LocalDate expiryDate;
    private UUID userId;
}
