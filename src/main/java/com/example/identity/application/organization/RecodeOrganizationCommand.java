package com.example.identity.application.organization;

import java.util.UUID;

public record RecodeOrganizationCommand(
        UUID organizationId,
        String code
) {
}
