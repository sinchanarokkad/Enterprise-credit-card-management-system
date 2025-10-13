package com.sinchana.credit_card_management_service.security;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RolePermissionService {

    private final Map<Role, Set<Permission>> rolePermissions = new HashMap<>();

    public RolePermissionService() {
        initializeRolePermissions();
    }

    private void initializeRolePermissions() {
        // ADMIN - Full access
        rolePermissions.put(Role.ADMIN, Set.of(
                Permission.USER_CREATE, Permission.USER_READ, Permission.USER_UPDATE, Permission.USER_DELETE,
                Permission.CARD_CREATE, Permission.CARD_READ, Permission.CARD_UPDATE, Permission.CARD_DELETE,
                Permission.CARD_APPROVE, Permission.CARD_REJECT,
                Permission.TRANSACTION_CREATE, Permission.TRANSACTION_READ, Permission.TRANSACTION_UPDATE, Permission.TRANSACTION_DELETE,
                Permission.APPLICATION_CREATE, Permission.APPLICATION_READ, Permission.APPLICATION_UPDATE,
                Permission.APPLICATION_APPROVE, Permission.APPLICATION_REJECT,
                Permission.PROFILE_CREATE, Permission.PROFILE_READ, Permission.PROFILE_UPDATE, Permission.PROFILE_DELETE,
                Permission.ADMIN_ACCESS, Permission.AUDIT_ACCESS, Permission.REPORT_ACCESS
        ));

        // MANAGER - Management access
        rolePermissions.put(Role.MANAGER, Set.of(
                Permission.USER_READ, Permission.USER_UPDATE,
                Permission.CARD_READ, Permission.CARD_UPDATE, Permission.CARD_APPROVE, Permission.CARD_REJECT,
                Permission.TRANSACTION_READ, Permission.TRANSACTION_UPDATE,
                Permission.APPLICATION_READ, Permission.APPLICATION_UPDATE, Permission.APPLICATION_APPROVE, Permission.APPLICATION_REJECT,
                Permission.PROFILE_READ, Permission.PROFILE_UPDATE,
                Permission.REPORT_ACCESS
        ));

        // BANK_EMPLOYEE - Employee access
        rolePermissions.put(Role.BANK_EMPLOYEE, Set.of(
                Permission.USER_READ,
                Permission.CARD_READ, Permission.CARD_UPDATE,
                Permission.TRANSACTION_READ, Permission.TRANSACTION_UPDATE,
                Permission.APPLICATION_READ, Permission.APPLICATION_UPDATE,
                Permission.PROFILE_READ, Permission.PROFILE_UPDATE
        ));

        // CUSTOMER - Limited access
        rolePermissions.put(Role.CUSTOMER, Set.of(
                Permission.USER_READ, Permission.USER_UPDATE,
                Permission.CARD_READ,
                Permission.TRANSACTION_CREATE, Permission.TRANSACTION_READ,
                Permission.APPLICATION_CREATE, Permission.APPLICATION_READ,
                Permission.PROFILE_CREATE, Permission.PROFILE_READ, Permission.PROFILE_UPDATE
        ));

        // AUDITOR - Read-only access
        rolePermissions.put(Role.AUDITOR, Set.of(
                Permission.USER_READ,
                Permission.CARD_READ,
                Permission.TRANSACTION_READ,
                Permission.APPLICATION_READ,
                Permission.PROFILE_READ,
                Permission.AUDIT_ACCESS, Permission.REPORT_ACCESS
        ));
    }

    public Set<Permission> getPermissionsForRole(Role role) {
        return rolePermissions.getOrDefault(role, Collections.emptySet());
    }

    public boolean hasPermission(Role role, Permission permission) {
        return getPermissionsForRole(role).contains(permission);
    }

    public boolean hasAnyPermission(Role role, Permission... permissions) {
        Set<Permission> rolePermissions = getPermissionsForRole(role);
        return Arrays.stream(permissions).anyMatch(rolePermissions::contains);
    }

    public boolean hasAllPermissions(Role role, Permission... permissions) {
        Set<Permission> rolePermissions = getPermissionsForRole(role);
        return Arrays.stream(permissions).allMatch(rolePermissions::contains);
    }
}