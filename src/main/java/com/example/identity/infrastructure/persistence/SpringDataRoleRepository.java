package com.example.identity.infrastructure.persistence;

import com.example.identity.domain.role.Role;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

interface SpringDataRoleRepository extends CrudRepository<Role, UUID> {

    @Query("SELECT * FROM roles WHERE organization_id = :organizationId AND code = :code")
    Optional<Role> findByCode(@Param("organizationId") UUID organizationId, @Param("code") String code);

    @Query("SELECT CASE WHEN COUNT(1) > 0 THEN TRUE ELSE FALSE END FROM roles WHERE organization_id = :organizationId AND code = :code")
    boolean existsByCode(@Param("organizationId") UUID organizationId, @Param("code") String code);
}
