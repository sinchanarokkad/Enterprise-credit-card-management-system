package com.sinchana.credit_card_management_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "card_applications")
public class CardApplication {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String cardType; // "VISA", "MASTERCARD", "AMEX", "RUPAY"

    private double requestedLimit;

    private LocalDateTime applicationDate;

    private String status; // "APPLIED", "IN_PROGRESS", "APPROVED", "REJECTED"

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy; // Optional: admin who reviewed

    private LocalDateTime reviewDate; // Optional: when processed

    // Constructors
    public CardApplication() {}

    public CardApplication(User user, String cardType, double requestedLimit, LocalDateTime applicationDate, String status) {
        this.user = user;
        this.cardType = cardType;
        this.requestedLimit = requestedLimit;
        this.applicationDate = applicationDate;
        this.status = status;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }

    public double getRequestedLimit() { return requestedLimit; }
    public void setRequestedLimit(double requestedLimit) { this.requestedLimit = requestedLimit; }

    public LocalDateTime getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDateTime applicationDate) { this.applicationDate = applicationDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public User getReviewedBy() { return reviewedBy; }
    public void setReviewedBy(User reviewedBy) { this.reviewedBy = reviewedBy; }

    public LocalDateTime getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDateTime reviewDate) { this.reviewDate = reviewDate; }
}
