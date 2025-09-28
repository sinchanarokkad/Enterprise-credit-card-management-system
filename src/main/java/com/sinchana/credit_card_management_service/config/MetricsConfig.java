package com.sinchana.credit_card_management_service.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    @Bean
    public Counter userCreationCounter(MeterRegistry registry) {
        return Counter.builder("credit_card.users.created")
                .description("Number of users created")
                .register(registry);
    }

    @Bean
    public Counter transactionCounter(MeterRegistry registry) {
        return Counter.builder("credit_card.transactions.created")
                .description("Number of transactions created")
                .register(registry);
    }

    @Bean
    public Counter cardApplicationCounter(MeterRegistry registry) {
        return Counter.builder("credit_card.applications.submitted")
                .description("Number of card applications submitted")
                .register(registry);
    }

    @Bean
    public Timer transactionProcessingTimer(MeterRegistry registry) {
        return Timer.builder("credit_card.transactions.processing.time")
                .description("Time taken to process transactions")
                .register(registry);
    }

    @Bean
    public Counter kafkaEventPublishedCounter(MeterRegistry registry) {
        return Counter.builder("credit_card.kafka.events.published")
                .description("Number of Kafka events published")
                .register(registry);
    }

    @Bean
    public Counter kafkaEventConsumedCounter(MeterRegistry registry) {
        return Counter.builder("credit_card.kafka.events.consumed")
                .description("Number of Kafka events consumed")
                .register(registry);
    }

    @Bean
    public Counter redisCacheHitCounter(MeterRegistry registry) {
        return Counter.builder("credit_card.redis.cache.hits")
                .description("Number of Redis cache hits")
                .register(registry);
    }

    @Bean
    public Counter redisCacheMissCounter(MeterRegistry registry) {
        return Counter.builder("credit_card.redis.cache.misses")
                .description("Number of Redis cache misses")
                .register(registry);
    }
}
