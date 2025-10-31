package com.example.identity.application.account;

import java.util.UUID;

public record AssignRoleToAccountCommand(
        UUID accountId,
        UUID roleId
) {
}
