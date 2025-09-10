package com.bank.ccms.dto.response;

import lombok.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponse {
    private UUID id;
    private UUID userId;
    private String name;
    private String phone;
    private String address;
    private Double annualIncome;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
