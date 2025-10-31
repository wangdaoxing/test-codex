package com.example.identity.domain.role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {

    Optional<Role> findById(UUID id);

    Optional<Role> findByCode(UUID organizationId, String code);

    Role save(Role role);

    boolean existsByCode(UUID organizationId, String code);
}
