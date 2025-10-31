package com.example.identity.infrastructure.persistence.entity;

import com.example.identity.domain.role.Role;
import com.example.identity.domain.role.RolePermission;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Table("roles")
public final class RoleEntity {

    @Id
    private final UUID id;

    private final UUID organizationId;

    private final String code;

    private final String name;

    private final String description;

    private final Instant createdAt;

    private final Instant updatedAt;

    @Version
    private final Long version;

    @MappedCollection(idColumn = "role_id", keyColumn = "permission_name")
    private final Set<RolePermissionEntity> permissions;

    RoleEntity(Role role) {
        this(
                role.getId(),
                role.getOrganizationId(),
                role.getCode(),
                role.getName(),
                role.getDescription(),
                role.getCreatedAt(),
                role.getUpdatedAt(),
                role.getVersion(),
                role.getPermissions().stream()
                        .map(RolePermissionEntity::new)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

    @PersistenceCreator
    RoleEntity(UUID id,
               UUID organizationId,
               String code,
               String name,
               String description,
               Instant createdAt,
               Instant updatedAt,
               Long version,
               Set<RolePermissionEntity> permissions) {
        this.id = id;
        this.organizationId = organizationId;
        this.code = code;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
        this.permissions = permissions == null ? new LinkedHashSet<>() : new LinkedHashSet<>(permissions);
    }

    Role toDomain() {
        Set<RolePermission> domainPermissions = permissions.stream()
                .map(RolePermissionEntity::toDomain)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return new Role(id, organizationId, code, name, description, createdAt, updatedAt, version, domainPermissions);
    }
}
