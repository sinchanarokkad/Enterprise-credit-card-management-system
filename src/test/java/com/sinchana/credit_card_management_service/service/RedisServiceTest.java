package com.sinchana.credit_card_management_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private RedisService redisService;

    @BeforeEach
    void setUp() {
        lenient().when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void set_ShouldCallRedisTemplateSet_WhenValidKeyAndValue() {
        // Given
        String key = "test-key";
        String value = "test-value";

        // When
        redisService.set(key, value);

        // Then
        verify(valueOperations).set(key, value);
    }

    @Test
    void setWithTimeout_ShouldCallRedisTemplateSetWithTimeout_WhenValidParameters() {
        // Given
        String key = "test-key";
        String value = "test-value";
        long timeout = 60L;
        TimeUnit unit = TimeUnit.SECONDS;

        // When
        redisService.set(key, value, timeout, unit);

        // Then
        verify(valueOperations).set(key, value, timeout, unit);
    }

    @Test
    void setWithDuration_ShouldCallRedisTemplateSetWithDuration_WhenValidParameters() {
        // Given
        String key = "test-key";
        String value = "test-value";
        Duration timeout = Duration.ofMinutes(10);

        // When
        redisService.set(key, value, timeout);

        // Then
        verify(valueOperations).set(key, value, timeout);
    }

    @Test
    void get_ShouldReturnValue_WhenKeyExists() {
        // Given
        String key = "test-key";
        String expectedValue = "test-value";
        when(valueOperations.get(key)).thenReturn(expectedValue);

        // When
        Object result = redisService.get(key);

        // Then
        assertEquals(expectedValue, result);
        verify(valueOperations).get(key);
    }

    @Test
    void getWithType_ShouldReturnTypedValue_WhenKeyExistsAndTypeMatches() {
        // Given
        String key = "test-key";
        String expectedValue = "test-value";
        when(valueOperations.get(key)).thenReturn(expectedValue);

        // When
        String result = redisService.get(key, String.class);

        // Then
        assertEquals(expectedValue, result);
        verify(valueOperations).get(key);
    }

    @Test
    void getWithType_ShouldReturnNull_WhenKeyDoesNotExist() {
        // Given
        String key = "test-key";
        when(valueOperations.get(key)).thenReturn(null);

        // When
        String result = redisService.get(key, String.class);

        // Then
        assertNull(result);
        verify(valueOperations).get(key);
    }

    @Test
    void delete_ShouldCallRedisTemplateDelete_WhenValidKey() {
        // Given
        String key = "test-key";
        when(redisTemplate.delete(key)).thenReturn(true);

        // When
        redisService.delete(key);

        // Then
        verify(redisTemplate).delete(key);
    }

    @Test
    void hasKey_ShouldReturnTrue_WhenKeyExists() {
        // Given
        String key = "test-key";
        when(redisTemplate.hasKey(key)).thenReturn(true);

        // When
        boolean result = redisService.hasKey(key);

        // Then
        assertTrue(result);
        verify(redisTemplate).hasKey(key);
    }

    @Test
    void hasKey_ShouldReturnFalse_WhenKeyDoesNotExist() {
        // Given
        String key = "test-key";
        when(redisTemplate.hasKey(key)).thenReturn(false);

        // When
        boolean result = redisService.hasKey(key);

        // Then
        assertFalse(result);
        verify(redisTemplate).hasKey(key);
    }

    @Test
    void setExpire_ShouldCallRedisTemplateExpire_WhenValidParameters() {
        // Given
        String key = "test-key";
        long timeout = 60L;
        TimeUnit unit = TimeUnit.SECONDS;

        // When
        redisService.setExpire(key, timeout, unit);

        // Then
        verify(redisTemplate).expire(key, timeout, unit);
    }

    @Test
    void getExpire_ShouldReturnExpireTime_WhenKeyExists() {
        // Given
        String key = "test-key";
        Long expectedExpire = 60L;
        when(redisTemplate.getExpire(key)).thenReturn(expectedExpire);

        // When
        Long result = redisService.getExpire(key);

        // Then
        assertEquals(expectedExpire, result);
        verify(redisTemplate).getExpire(key);
    }

    @Test
    void increment_ShouldCallRedisTemplateIncrement_WhenValidKey() {
        // Given
        String key = "test-key";
        when(valueOperations.increment(key)).thenReturn(1L);

        // When
        redisService.increment(key);

        // Then
        verify(valueOperations).increment(key);
    }

    @Test
    void incrementWithDelta_ShouldCallRedisTemplateIncrementWithDelta_WhenValidParameters() {
        // Given
        String key = "test-key";
        long delta = 5L;
        when(valueOperations.increment(key, delta)).thenReturn(5L);

        // When
        redisService.increment(key, delta);

        // Then
        verify(valueOperations).increment(key, delta);
    }
}

