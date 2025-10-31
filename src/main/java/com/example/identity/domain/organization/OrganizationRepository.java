package com.example.identity.domain.organization;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository {

    Optional<Organization> findById(UUID id);

    Optional<Organization> findByCode(String code);

    Organization save(Organization organization);

    boolean existsByCode(String code);
}
