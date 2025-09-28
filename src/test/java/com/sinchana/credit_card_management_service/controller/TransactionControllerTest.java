package com.sinchana.credit_card_management_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinchana.credit_card_management_service.entity.Card;
import com.sinchana.credit_card_management_service.entity.Transaction;
import com.sinchana.credit_card_management_service.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    private Transaction testTransaction;
    private Card testCard;
    private UUID testCardId;

    @BeforeEach
    void setUp() {
        testCardId = UUID.randomUUID();
        
        testCard = new Card();
        testCard.setId(testCardId);
        testCard.setCardNumber("1234567890123456");
        testCard.setStatus("ACTIVE");
        testCard.setCreditLimit(new BigDecimal("5000.00"));

        testTransaction = new Transaction();
        testTransaction.setId(UUID.randomUUID());
        testTransaction.setCard(testCard);
        testTransaction.setAmount(new BigDecimal("100.00"));
        testTransaction.setDescription("Test transaction");
        testTransaction.setTransactionDate(LocalDateTime.now());
        testTransaction.setStatus("COMPLETED");
    }

    @Test
    void createTransaction_ShouldReturnCreatedTransaction_WhenValidRequest() throws Exception {
        // Given
        when(transactionService.simulateTransaction(any(Transaction.class))).thenReturn(testTransaction);

        // When & Then
        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTransaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testTransaction.getId().toString()))
                .andExpect(jsonPath("$.amount").value(100.00))
                .andExpect(jsonPath("$.description").value("Test transaction"))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void getTransactionsByCard_ShouldReturnTransactionList_WhenCardExists() throws Exception {
        // Given
        List<Transaction> transactions = Arrays.asList(testTransaction);
        when(transactionService.getTransactionsByCardId(testCardId)).thenReturn(transactions);

        // When & Then
        mockMvc.perform(get("/api/transactions/{cardId}", testCardId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(testTransaction.getId().toString()))
                .andExpect(jsonPath("$[0].amount").value(100.00))
                .andExpect(jsonPath("$[0].description").value("Test transaction"));
    }

    @Test
    void getTransactionsByCard_ShouldReturnEmptyList_WhenNoTransactions() throws Exception {
        // Given
        when(transactionService.getTransactionsByCardId(testCardId)).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/transactions/{cardId}", testCardId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void createTransaction_ShouldReturnBadRequest_WhenInvalidRequest() throws Exception {
        // Given
        Transaction invalidTransaction = new Transaction();
        // Missing required fields

        // When & Then
        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidTransaction)))
                .andExpect(status().isBadRequest());
    }
}
