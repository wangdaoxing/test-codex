package com.example.identity.application.role;

import java.util.UUID;

public record GrantPermissionToRoleCommand(
        UUID roleId,
        PermissionDefinition permission
) {
}
