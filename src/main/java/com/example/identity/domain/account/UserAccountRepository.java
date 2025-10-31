package com.example.identity.domain.account;

import com.example.identity.domain.shared.EmailAddress;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository {

    Optional<UserAccount> findById(UUID id);

    Optional<UserAccount> findByUsername(String username);

    Optional<UserAccount> findByEmail(EmailAddress emailAddress);

    UserAccount save(UserAccount account);

    boolean existsByUsername(String username);

    boolean existsByEmail(EmailAddress emailAddress);
}
