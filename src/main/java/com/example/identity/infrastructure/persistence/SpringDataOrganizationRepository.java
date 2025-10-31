package com.example.identity.infrastructure.persistence;

import com.example.identity.infrastructure.persistence.entity.OrganizationEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

interface SpringDataOrganizationRepository extends CrudRepository<OrganizationEntity, UUID> {

    @Query("SELECT * FROM organizations WHERE code = :code")
    Optional<OrganizationEntity> findByCode(@Param("code") String code);

    @Query("SELECT CASE WHEN COUNT(1) > 0 THEN TRUE ELSE FALSE END FROM organizations WHERE code = :code")
    boolean existsByCode(@Param("code") String code);
}
