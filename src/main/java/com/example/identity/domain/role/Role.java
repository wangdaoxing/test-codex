package com.example.identity.domain.role;

import com.example.identity.domain.shared.DomainException;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Table("roles")
public class Role {

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

    @MappedCollection(idColumn = "role_id")
    private final Set<RolePermission> permissions;

    public static Role create(UUID id, UUID organizationId, String code, String name, String description) {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (organizationId == null) {
            throw new DomainException("Organization id is required");
        }
        if (code == null || code.isBlank()) {
            throw new DomainException("Role code is required");
        }
        if (name == null || name.isBlank()) {
            throw new DomainException("Role name is required");
        }
        return new Role(id, organizationId, normalize(code), name.trim(), description, Instant.now(), Instant.now(), null, new LinkedHashSet<>());
    }

    @PersistenceCreator
    public Role(UUID id,
                UUID organizationId,
                String code,
                String name,
                String description,
                Instant createdAt,
                Instant updatedAt,
                Long version,
                Set<RolePermission> permissions) {
        this.id = Objects.requireNonNull(id, "id");
        this.organizationId = Objects.requireNonNull(organizationId, "organizationId");
        this.code = normalize(code);
        this.name = Objects.requireNonNull(name, "name");
        this.description = description;
        this.createdAt = createdAt == null ? Instant.now() : createdAt;
        this.updatedAt = updatedAt == null ? createdAt : updatedAt;
        this.version = version;
        this.permissions = permissions == null ? new LinkedHashSet<>() : new LinkedHashSet<>(permissions);
    }

    private static String normalize(String code) {
        if (code == null || code.isBlank()) {
            throw new DomainException("Role code is required");
        }
        return code.trim().toUpperCase();
    }

    public Role rename(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new DomainException("Role name is required");
        }
        return new Role(id, organizationId, code, newName.trim(), description, createdAt, Instant.now(), version, permissions);
    }

    public Role describe(String newDescription) {
        return new Role(id, organizationId, code, name, newDescription, createdAt, Instant.now(), version, permissions);
    }

    public Role grantPermission(String permissionName, String permissionDescription) {
        RolePermission permission = new RolePermission(permissionName, permissionDescription);
        if (permissions.contains(permission)) {
            throw new DomainException("Permission already granted to role");
        }
        Set<RolePermission> updated = new LinkedHashSet<>(permissions);
        updated.add(permission);
        return new Role(id, organizationId, code, name, description, createdAt, Instant.now(), version, updated);
    }

    public Role revokePermission(String permissionName) {
        RolePermission permission = new RolePermission(permissionName, null);
        Set<RolePermission> updated = new LinkedHashSet<>(permissions);
        if (!updated.remove(permission)) {
            throw new DomainException("Permission not assigned to role");
        }
        return new Role(id, organizationId, code, name, description, createdAt, Instant.now(), version, updated);
    }

    public UUID getId() {
        return id;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Long getVersion() {
        return version;
    }

    public Set<RolePermission> getPermissions() {
        return Set.copyOf(permissions);
    }
}
