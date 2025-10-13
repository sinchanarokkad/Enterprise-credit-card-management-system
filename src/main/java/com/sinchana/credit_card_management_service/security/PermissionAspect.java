package com.sinchana.credit_card_management_service.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PermissionAspect {

    private final RolePermissionService rolePermissionService;

    public PermissionAspect(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @Around("@annotation(requirePermission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint, RequirePermission requirePermission) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User not authenticated");
        }

        // Get user role from authentication
        String roleString = authentication.getAuthorities().iterator().next().getAuthority();
        Role userRole = Role.valueOf(roleString);

        // Check permissions
        boolean hasPermission;
        if (requirePermission.requireAll()) {
            hasPermission = rolePermissionService.hasAllPermissions(userRole, requirePermission.value());
        } else {
            hasPermission = rolePermissionService.hasAnyPermission(userRole, requirePermission.value());
        }

        if (!hasPermission) {
            throw new SecurityException("Insufficient permissions. Required: " +
                    java.util.Arrays.toString(requirePermission.value()) +
                    ", User role: " + userRole);
        }

        return joinPoint.proceed();
    }
}