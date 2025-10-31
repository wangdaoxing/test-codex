package com.example.identity.application.account;

import java.util.UUID;

public record RevokeRoleFromAccountCommand(
        UUID accountId,
        UUID roleId
) {
}
