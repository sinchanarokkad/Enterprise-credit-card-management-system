package com.sinchana.credit_card_management_service.controller;

import com.sinchana.credit_card_management_service.dto.request.CardApplicationRequestDTO;
import com.sinchana.credit_card_management_service.dto.response.CardApplicationResponseDTO;
import com.sinchana.credit_card_management_service.entity.CardApplication;
import com.sinchana.credit_card_management_service.mapper.CardApplicationMapper;
import com.sinchana.credit_card_management_service.service.CardApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/card-applications")
public class CardApplicationController {

    private final CardApplicationService cardApplicationService;
    private final CardApplicationMapper cardApplicationMapper;

    public CardApplicationController(CardApplicationService cardApplicationService,
                                     CardApplicationMapper cardApplicationMapper) {
        this.cardApplicationService = cardApplicationService;
        this.cardApplicationMapper = cardApplicationMapper;
    }

    // Apply for a card
    @PostMapping
    public CardApplicationResponseDTO applyForCard(@RequestBody CardApplicationRequestDTO requestDTO) {
        CardApplication application = cardApplicationMapper.toEntity(requestDTO);

        CardApplication saved = cardApplicationService.applyForCard(application, requestDTO.getUserId());

        return cardApplicationMapper.toResponseDTO(saved);
    }

    // Get all applications by a user
    @GetMapping("/user/{userId}")
    public List<CardApplicationResponseDTO> getApplicationsByUserId(@PathVariable UUID userId) {
        return cardApplicationService.getApplicationsByUserId(userId).stream()
                .map(cardApplicationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
