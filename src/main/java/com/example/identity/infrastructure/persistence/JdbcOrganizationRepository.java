package com.example.identity.infrastructure.persistence;

import com.example.identity.domain.organization.Organization;
import com.example.identity.domain.organization.OrganizationRepository;
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
        return repository.findById(id);
    }

    @Override
    public Optional<Organization> findByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public Organization save(Organization organization) {
        return repository.save(organization);
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }
}
