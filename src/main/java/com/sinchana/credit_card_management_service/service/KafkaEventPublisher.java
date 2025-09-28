package com.sinchana.credit_card_management_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinchana.credit_card_management_service.event.CreditCardEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEventPublisher.class);
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishEvent(String topic, CreditCardEvent event) {
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(topic, event.getUserId(), eventJson);
            logger.info("Published event {} to topic {} for user {}", event.getEventType(), topic, event.getUserId());
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize event: {}", e.getMessage());
        }
    }

    public void publishCardApplicationEvent(String userId, Object applicationData) {
        CreditCardEvent event = new CreditCardEvent("CARD_APPLICATION_SUBMITTED", userId, null, applicationData);
        publishEvent("card-applications", event);
    }

    public void publishCardStatusChangeEvent(String userId, String cardId, Object statusData) {
        CreditCardEvent event = new CreditCardEvent("CARD_STATUS_CHANGED", userId, cardId, statusData);
        publishEvent("card-status-changes", event);
    }

    public void publishTransactionEvent(String userId, String cardId, Object transactionData) {
        CreditCardEvent event = new CreditCardEvent("TRANSACTION_CREATED", userId, cardId, transactionData);
        publishEvent("transactions", event);
    }

    public void publishUserProfileEvent(String userId, Object profileData) {
        CreditCardEvent event = new CreditCardEvent("USER_PROFILE_UPDATED", userId, null, profileData);
        publishEvent("user-profiles", event);
    }
}
