package com.example.identity.infrastructure.persistence;

import com.example.identity.domain.account.UserAccount;
import com.example.identity.domain.account.UserAccountRepository;
import com.example.identity.domain.shared.EmailAddress;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcUserAccountRepository implements UserAccountRepository {

    private final SpringDataUserAccountRepository repository;

    public JdbcUserAccountRepository(SpringDataUserAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<UserAccount> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<UserAccount> findByEmail(EmailAddress emailAddress) {
        return repository.findByEmail(emailAddress.getValue());
    }

    @Override
    public UserAccount save(UserAccount account) {
        return repository.save(account);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(EmailAddress emailAddress) {
        return repository.existsByEmail(emailAddress.getValue());
    }
}
