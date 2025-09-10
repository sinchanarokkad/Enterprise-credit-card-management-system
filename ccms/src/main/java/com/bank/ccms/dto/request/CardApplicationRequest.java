package com.bank.ccms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CardApplicationRequest {
    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotBlank(message = "Card type is required")
    private String cardType;

    @NotNull(message = "Requested limit is required")
    private Double requestedLimit;
}
