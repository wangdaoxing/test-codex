package com.example.identity.infrastructure.persistence;

import com.example.identity.domain.role.Role;
import com.example.identity.domain.role.RoleRepository;
import com.example.identity.infrastructure.persistence.entity.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcRoleRepository implements RoleRepository {

    private final SpringDataRoleRepository repository;

    public JdbcRoleRepository(SpringDataRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Role> findById(UUID id) {
        return repository.findById(id).map(RoleEntity::toDomain);
    }

    @Override
    public Optional<Role> findByCode(UUID organizationId, String code) {
        return repository.findByCode(organizationId, code).map(RoleEntity::toDomain);
    }

    @Override
    public Role save(Role role) {
        RoleEntity saved = repository.save(new RoleEntity(role));
        return saved.toDomain();
    }

    @Override
    public boolean existsByCode(UUID organizationId, String code) {
        return repository.existsByCode(organizationId, code);
    }
}
