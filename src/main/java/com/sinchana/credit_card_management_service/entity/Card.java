package com.sinchana.credit_card_management_service.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String cardType; // "VISA", "MASTERCARD", "AMEX", "RUPAY"

    @Column(nullable = false)
    private String status; // "ACTIVE", "BLOCKED", "INACTIVE", "EXPIRED"

    private double creditLimit;
    private double availableLimit;

    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructors
    public Card() {}

    public Card(String cardNumber, String cardType, String status, double creditLimit, double availableLimit, LocalDate expiryDate, User user) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.status = status;
        this.creditLimit = creditLimit;
        this.availableLimit = availableLimit;
        this.expiryDate = expiryDate;
        this.user = user;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getCreditLimit() { return creditLimit; }
    public void setCreditLimit(double creditLimit) { this.creditLimit = creditLimit; }

    public double getAvailableLimit() { return availableLimit; }
    public void setAvailableLimit(double availableLimit) { this.availableLimit = availableLimit; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
