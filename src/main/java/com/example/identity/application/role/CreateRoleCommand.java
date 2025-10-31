package com.example.identity.application.role;

import java.util.Set;
import java.util.UUID;

public record CreateRoleCommand(
        UUID organizationId,
        String code,
        String name,
        String description,
        Set<PermissionDefinition> permissions
) {
}
