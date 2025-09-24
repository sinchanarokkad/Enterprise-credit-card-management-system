package com.sinchana.credit_card_management_service.dto.request;

import java.util.UUID;

public class CardApplicationRequestDTO {

    private UUID userId;       // Frontend sends this
    private String cardType;   // Frontend sends this

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }
}
