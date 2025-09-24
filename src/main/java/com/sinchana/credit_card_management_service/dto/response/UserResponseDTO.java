package com.sinchana.credit_card_management_service.dto.response;

import java.util.UUID;

public class UserResponseDTO {

    private UUID id;
    private String email;
    private String userType;

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
}
