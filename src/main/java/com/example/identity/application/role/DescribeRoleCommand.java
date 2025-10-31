package com.example.identity.application.role;

import java.util.UUID;

public record DescribeRoleCommand(
        UUID roleId,
        String description
) {
}
