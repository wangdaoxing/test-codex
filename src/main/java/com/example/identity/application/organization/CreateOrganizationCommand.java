package com.example.identity.application.organization;

import java.util.UUID;

public record CreateOrganizationCommand(
        String code,
        String name,
        UUID parentId,
        String description
) {
}
