package com.example.identity.application.organization;

import java.util.UUID;

public record RenameOrganizationCommand(
        UUID organizationId,
        String name
) {
}
