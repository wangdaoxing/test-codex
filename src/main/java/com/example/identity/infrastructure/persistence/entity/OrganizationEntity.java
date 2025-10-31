package com.example.identity.infrastructure.persistence.entity;

import com.example.identity.domain.organization.Organization;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("organizations")
public final class OrganizationEntity {

    @Id
    private final UUID id;

    private final String code;

    private final String name;

    private final UUID parentId;

    private final String description;

    private final Instant createdAt;

    private final Instant updatedAt;

    @Version
    private final Long version;

    OrganizationEntity(Organization organization) {
        this(
                organization.getId(),
                organization.getCode(),
                organization.getName(),
                organization.getParentId(),
                organization.getDescription(),
                organization.getCreatedAt(),
                organization.getUpdatedAt(),
                organization.getVersion()
        );
    }

    @PersistenceCreator
    OrganizationEntity(UUID id,
                       String code,
                       String name,
                       UUID parentId,
                       String description,
                       Instant createdAt,
                       Instant updatedAt,
                       Long version) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.parentId = parentId;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    Organization toDomain() {
        return new Organization(id, code, name, parentId, description, createdAt, updatedAt, version);
    }
}
