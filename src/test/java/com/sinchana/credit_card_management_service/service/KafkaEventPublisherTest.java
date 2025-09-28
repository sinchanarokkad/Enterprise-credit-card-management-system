package com.sinchana.credit_card_management_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinchana.credit_card_management_service.event.CreditCardEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaEventPublisherTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaEventPublisher kafkaEventPublisher;

    private CreditCardEvent testEvent;
    private String testEventJson;

    @BeforeEach
    void setUp() {
        testEvent = new CreditCardEvent("TEST_EVENT", "user123", "card456", "test data");
        testEventJson = "{\"eventType\":\"TEST_EVENT\",\"userId\":\"user123\",\"cardId\":\"card456\",\"data\":\"test data\"}";
    }

    @Test
    void publishEvent_ShouldSendToKafka_WhenValidEvent() throws JsonProcessingException {
        // Given
        String topic = "test-topic";
        when(objectMapper.writeValueAsString(testEvent)).thenReturn(testEventJson);
        when(kafkaTemplate.send(eq(topic), eq("user123"), eq(testEventJson)))
                .thenReturn(CompletableFuture.completedFuture(null));

        // When
        kafkaEventPublisher.publishEvent(topic, testEvent);

        // Then
        verify(objectMapper).writeValueAsString(testEvent);
        verify(kafkaTemplate).send(topic, "user123", testEventJson);
    }

    @Test
    void publishEvent_ShouldHandleJsonProcessingException_WhenSerializationFails() throws JsonProcessingException {
        // Given
        String topic = "test-topic";
        when(objectMapper.writeValueAsString(testEvent)).thenThrow(new JsonProcessingException("Serialization failed") {});

        // When
        kafkaEventPublisher.publishEvent(topic, testEvent);

        // Then
        verify(objectMapper).writeValueAsString(testEvent);
        verify(kafkaTemplate, never()).send(anyString(), anyString(), anyString());
    }

    @Test
    void publishCardApplicationEvent_ShouldCreateAndPublishEvent_WhenValidData() throws JsonProcessingException {
        // Given
        String userId = "user123";
        Object applicationData = "application data";
        when(objectMapper.writeValueAsString(any(CreditCardEvent.class))).thenReturn(testEventJson);
        when(kafkaTemplate.send(anyString(), anyString(), anyString()))
                .thenReturn(CompletableFuture.completedFuture(null));

        // When
        kafkaEventPublisher.publishCardApplicationEvent(userId, applicationData);

        // Then
        verify(objectMapper).writeValueAsString(any(CreditCardEvent.class));
        verify(kafkaTemplate).send(eq("card-applications"), eq(userId), anyString());
    }

    @Test
    void publishCardStatusChangeEvent_ShouldCreateAndPublishEvent_WhenValidData() throws JsonProcessingException {
        // Given
        String userId = "user123";
        String cardId = "card456";
        Object statusData = "status data";
        when(objectMapper.writeValueAsString(any(CreditCardEvent.class))).thenReturn(testEventJson);
        when(kafkaTemplate.send(anyString(), anyString(), anyString()))
                .thenReturn(CompletableFuture.completedFuture(null));

        // When
        kafkaEventPublisher.publishCardStatusChangeEvent(userId, cardId, statusData);

        // Then
        verify(objectMapper).writeValueAsString(any(CreditCardEvent.class));
        verify(kafkaTemplate).send(eq("card-status-changes"), eq(userId), anyString());
    }

    @Test
    void publishTransactionEvent_ShouldCreateAndPublishEvent_WhenValidData() throws JsonProcessingException {
        // Given
        String userId = "user123";
        String cardId = "card456";
        Object transactionData = "transaction data";
        when(objectMapper.writeValueAsString(any(CreditCardEvent.class))).thenReturn(testEventJson);
        when(kafkaTemplate.send(anyString(), anyString(), anyString()))
                .thenReturn(CompletableFuture.completedFuture(null));

        // When
        kafkaEventPublisher.publishTransactionEvent(userId, cardId, transactionData);

        // Then
        verify(objectMapper).writeValueAsString(any(CreditCardEvent.class));
        verify(kafkaTemplate).send(eq("transactions"), eq(userId), anyString());
    }

    @Test
    void publishUserProfileEvent_ShouldCreateAndPublishEvent_WhenValidData() throws JsonProcessingException {
        // Given
        String userId = "user123";
        Object profileData = "profile data";
        when(objectMapper.writeValueAsString(any(CreditCardEvent.class))).thenReturn(testEventJson);
        when(kafkaTemplate.send(anyString(), anyString(), anyString()))
                .thenReturn(CompletableFuture.completedFuture(null));

        // When
        kafkaEventPublisher.publishUserProfileEvent(userId, profileData);

        // Then
        verify(objectMapper).writeValueAsString(any(CreditCardEvent.class));
        verify(kafkaTemplate).send(eq("user-profiles"), eq(userId), anyString());
    }
}
