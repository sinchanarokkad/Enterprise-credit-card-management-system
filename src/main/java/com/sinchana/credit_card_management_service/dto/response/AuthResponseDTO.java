package com.sinchana.credit_card_management_service.dto.response;

public class AuthResponseDTO {
    private String token;

    public AuthResponseDTO(String token) { this.token = token; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
