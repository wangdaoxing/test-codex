package com.example.identity.application.role;

import java.util.UUID;

public record RenameRoleCommand(
        UUID roleId,
        String name
) {
}
