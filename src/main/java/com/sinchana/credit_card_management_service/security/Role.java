package com.sinchana.credit_card_management_service.security;

public enum Role {
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER"),
    BANK_EMPLOYEE("BANK_EMPLOYEE"),
    MANAGER("MANAGER"),
    AUDITOR("AUDITOR");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}