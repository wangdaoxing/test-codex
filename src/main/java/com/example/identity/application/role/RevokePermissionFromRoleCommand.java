package com.example.identity.application.role;

import java.util.UUID;

public record RevokePermissionFromRoleCommand(
        UUID roleId,
        String permissionName
) {
}
