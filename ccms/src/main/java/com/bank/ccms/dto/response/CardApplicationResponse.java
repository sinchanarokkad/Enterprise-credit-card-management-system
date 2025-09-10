package com.bank.ccms.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CardApplicationResponse {
    private UUID id;
    private UUID userId;
    private String cardType;
    private Double requestedLimit;
    private LocalDateTime applicationDate;
    private String status;
    private UUID reviewedBy;
    private LocalDateTime reviewDate;
}
