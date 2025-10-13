package com.sinchana.credit_card_management_service.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    private final RateLimitingService rateLimitingService;

    public RateLimitingInterceptor(RateLimitingService rateLimitingService) {
        this.rateLimitingService = rateLimitingService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        // Get user identifier
        String userIdentifier = getUserIdentifier(request);

        // Apply different rate limits based on endpoint
        boolean isAllowed = true;

        if (requestURI.startsWith("/api/auth/")) {
            // Authentication endpoints - stricter limits
            isAllowed = rateLimitingService.isLoginAttemptAllowed(userIdentifier);
        } else if (requestURI.startsWith("/api/cards/")) {
            // Card operations - moderate limits
            isAllowed = rateLimitingService.isCardOperationAllowed(userIdentifier);
        } else if (requestURI.startsWith("/api/")) {
            // General API endpoints
            isAllowed = rateLimitingService.isApiCallAllowed(userIdentifier, requestURI);
        }

        if (!isAllowed) {
            sendRateLimitExceededResponse(response);
            return false;
        }

        return true;
    }

    private String getUserIdentifier(HttpServletRequest request) {
        // Try to get user ID from authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // This would be the user email/ID
        }

        // Fallback to IP address
        String clientIp = getClientIpAddress(request);
        return "ip:" + clientIp;
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }

    private void sendRateLimitExceededResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = """
            {
                "error": "Rate limit exceeded",
                "message": "Too many requests. Please try again later.",
                "status": 429
            }
            """;

        response.getWriter().write(jsonResponse);
    }
}