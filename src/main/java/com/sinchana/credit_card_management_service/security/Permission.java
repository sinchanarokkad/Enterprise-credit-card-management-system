package com.sinchana.credit_card_management_service.security;

public enum Permission {
    // User Management
    USER_CREATE("user:create"),
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),

    // Card Management
    CARD_CREATE("card:create"),
    CARD_READ("card:read"),
    CARD_UPDATE("card:update"),
    CARD_DELETE("card:delete"),
    CARD_APPROVE("card:approve"),
    CARD_REJECT("card:reject"),

    // Transaction Management
    TRANSACTION_CREATE("transaction:create"),
    TRANSACTION_READ("transaction:read"),
    TRANSACTION_UPDATE("transaction:update"),
    TRANSACTION_DELETE("transaction:delete"),

    // Application Management
    APPLICATION_CREATE("application:create"),
    APPLICATION_READ("application:read"),
    APPLICATION_UPDATE("application:update"),
    APPLICATION_APPROVE("application:approve"),
    APPLICATION_REJECT("application:reject"),

    // Profile Management
    PROFILE_CREATE("profile:create"),
    PROFILE_READ("profile:read"),
    PROFILE_UPDATE("profile:update"),
    PROFILE_DELETE("profile:delete"),

    // Admin Operations
    ADMIN_ACCESS("admin:access"),
    AUDIT_ACCESS("audit:access"),
    REPORT_ACCESS("report:access");

    private final String value;

    Permission(String value) {
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