package com.example.identity.infrastructure.persistence;

import com.example.identity.domain.account.UserAccount;
import com.example.identity.domain.account.UserAccountRepository;
import com.example.identity.domain.shared.EmailAddress;
import com.example.identity.infrastructure.persistence.entity.UserAccountEntity;
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
        return repository.findById(id).map(UserAccountEntity::toDomain);
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        return repository.findByUsername(username).map(UserAccountEntity::toDomain);
    }

    @Override
    public Optional<UserAccount> findByEmail(EmailAddress emailAddress) {
        return repository.findByEmail(emailAddress.getValue()).map(UserAccountEntity::toDomain);
    }

    @Override
    public UserAccount save(UserAccount account) {
        UserAccountEntity saved = repository.save(new UserAccountEntity(account));
        return saved.toDomain();
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
