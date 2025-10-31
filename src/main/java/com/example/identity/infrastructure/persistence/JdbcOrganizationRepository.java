package com.example.identity.infrastructure.persistence;

import com.example.identity.domain.organization.Organization;
import com.example.identity.domain.organization.OrganizationRepository;
import com.example.identity.infrastructure.persistence.entity.OrganizationEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcOrganizationRepository implements OrganizationRepository {

    private final SpringDataOrganizationRepository repository;

    public JdbcOrganizationRepository(SpringDataOrganizationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Organization> findById(UUID id) {
        return repository.findById(id).map(OrganizationEntity::toDomain);
    }

    @Override
    public Optional<Organization> findByCode(String code) {
        return repository.findByCode(code).map(OrganizationEntity::toDomain);
    }

    @Override
    public Organization save(Organization organization) {
        OrganizationEntity saved = repository.save(new OrganizationEntity(organization));
        return saved.toDomain();
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }
}
