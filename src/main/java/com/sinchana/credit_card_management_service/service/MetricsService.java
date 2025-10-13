package com.sinchana.credit_card_management_service.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private final MeterRegistry meterRegistry;

    private final Counter userCreationCounter;
    private final Counter transactionCounter;
    private final Counter cardApplicationCounter;
    private final Timer transactionProcessingTimer;
    private final Counter kafkaEventPublishedCounter;
    private final Counter kafkaEventConsumedCounter;
    private final Counter redisCacheHitCounter;
    private final Counter redisCacheMissCounter;

    public MetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

        this.userCreationCounter = Counter.builder("credit_card.users.created")
                .description("Number of users created")
                .register(meterRegistry);

        this.transactionCounter = Counter.builder("credit_card.transactions.created")
                .description("Number of transactions created")
                .register(meterRegistry);

        this.cardApplicationCounter = Counter.builder("credit_card.applications.submitted")
                .description("Number of card applications submitted")
                .register(meterRegistry);

        this.transactionProcessingTimer = Timer.builder("credit_card.transactions.processing.time")
                .description("Time taken to process transactions")
                .register(meterRegistry);

        this.kafkaEventPublishedCounter = Counter.builder("credit_card.kafka.events.published")
                .description("Number of Kafka events published")
                .register(meterRegistry);

        this.kafkaEventConsumedCounter = Counter.builder("credit_card.kafka.events.consumed")
                .description("Number of Kafka events consumed")
                .register(meterRegistry);

        this.redisCacheHitCounter = Counter.builder("credit_card.redis.cache.hits")
                .description("Number of Redis cache hits")
                .register(meterRegistry);

        this.redisCacheMissCounter = Counter.builder("credit_card.redis.cache.misses")
                .description("Number of Redis cache misses")
                .register(meterRegistry);
    }

    public void incrementUserCreation() {
        userCreationCounter.increment();
    }

    public void incrementTransaction() {
        transactionCounter.increment();
    }

    public void incrementCardApplication() {
        cardApplicationCounter.increment();
    }

    /**
     * Start transaction processing timer.
     */
    public Timer.Sample startTransactionProcessingTimer() {
        return Timer.start(meterRegistry);
    }

    /**
     * Stop transaction processing timer.
     */
    public void stopTransactionProcessingTimer(Timer.Sample sample) {
        sample.stop(transactionProcessingTimer);
    }

    public void incrementKafkaEventPublished() {
        kafkaEventPublishedCounter.increment();
    }

    public void incrementKafkaEventConsumed() {
        kafkaEventConsumedCounter.increment();
    }

    public void incrementRedisCacheHit() {
        redisCacheHitCounter.increment();
    }

    public void incrementRedisCacheMiss() {
        redisCacheMissCounter.increment();
    }
}
