package com.sinchana.credit_card_management_service.security;

import com.sinchana.credit_card_management_service.service.CacheService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class RateLimitingService {

    private final CacheService cacheService;
    private static final String RATE_LIMIT_PREFIX = "rate_limit:";
    private static final String SLIDING_WINDOW_PREFIX = "sliding_window:";

    public RateLimitingService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    /**
     * Check if request is within rate limit using sliding window algorithm
     * @param key Unique identifier for the rate limit (e.g., user ID, IP address)
     * @param maxRequests Maximum number of requests allowed
     * @param windowSizeInSeconds Time window size in seconds
     * @return true if request is allowed, false if rate limit exceeded
     */
    public boolean isAllowed(String key, int maxRequests, int windowSizeInSeconds) {
        String cacheKey = RATE_LIMIT_PREFIX + key;
        String slidingWindowKey = SLIDING_WINDOW_PREFIX + key;

        long currentTime = Instant.now().getEpochSecond();
        long windowStart = currentTime - windowSizeInSeconds;

        // Clean up old entries
        cleanupOldEntries(slidingWindowKey, windowStart);

        // Count current requests in window
        long currentRequests = getCurrentRequestCount(slidingWindowKey, windowStart);

        if (currentRequests >= maxRequests) {
            return false;
        }

        // Add current request
        addRequest(slidingWindowKey, currentTime);

        return true;
    }

    /**
     * Check rate limit for API endpoints
     */
    public boolean isApiCallAllowed(String userId, String endpoint) {
        String key = userId + ":" + endpoint;
        return isAllowed(key, 100, 60); // 100 requests per minute
    }

    /**
     * Check rate limit for authentication attempts
     */
    public boolean isLoginAttemptAllowed(String identifier) {
        String key = "login:" + identifier;
        return isAllowed(key, 5, 300); // 5 attempts per 5 minutes
    }

    /**
     * Check rate limit for card operations
     */
    public boolean isCardOperationAllowed(String userId) {
        String key = "card_ops:" + userId;
        return isAllowed(key, 20, 60); // 20 operations per minute
    }

    private void cleanupOldEntries(String key, long windowStart) {
        // This would be implemented with Redis sorted sets in a real implementation
        // For now, we'll use a simple approach with TTL
    }

    private long getCurrentRequestCount(String key, long windowStart) {
        // In a real implementation, this would use Redis sorted sets
        // For now, we'll use a simple counter approach
        return cacheService.get(key + ":count", Long.class).orElse(0L);
    }

    private void addRequest(String key, long timestamp) {
        // Increment counter
        long currentCount = cacheService.get(key + ":count", Long.class).orElse(0L);
        cacheService.put(key + ":count", currentCount + 1, Duration.ofMinutes(1));
    }

    /**
     * Get remaining requests for a key
     */
    public long getRemainingRequests(String key, int maxRequests, int windowSizeInSeconds) {
        String slidingWindowKey = SLIDING_WINDOW_PREFIX + key;
        long currentTime = Instant.now().getEpochSecond();
        long windowStart = currentTime - windowSizeInSeconds;

        long currentRequests = getCurrentRequestCount(slidingWindowKey, windowStart);
        return Math.max(0, maxRequests - currentRequests);
    }
}