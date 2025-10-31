package com.example.identity.application.account;

import java.util.UUID;

public record ChangeEmailCommand(
        UUID accountId,
        String newEmail
) {
}
