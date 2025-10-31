package com.example.identity.application.organization;

import java.util.UUID;

public record DescribeOrganizationCommand(
        UUID organizationId,
        String description
) {
}
