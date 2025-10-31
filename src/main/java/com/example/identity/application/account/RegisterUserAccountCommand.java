package com.example.identity.application.account;

import java.util.UUID;

public record RegisterUserAccountCommand(
        String username,
        String email,
        String passwordHash,
        UUID organizationId,
        String givenName,
        String familyName,
        String phoneNumber
) {
}
