package com.example.identity.domain.organization;

import com.example.identity.domain.shared.DomainException;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Table("organizations")
public class Organization {

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

    public static Organization create(UUID id, String code, String name, UUID parentId, String description) {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (code == null || code.isBlank()) {
            throw new DomainException("Organization code is required");
        }
        if (name == null || name.isBlank()) {
            throw new DomainException("Organization name is required");
        }
        Instant now = Instant.now();
        return new Organization(id, normalize(code), name.trim(), parentId, description, now, now, null);
    }

    @PersistenceCreator
    public Organization(UUID id,
                        String code,
                        String name,
                        UUID parentId,
                        String description,
                        Instant createdAt,
                        Instant updatedAt,
                        Long version) {
        this.id = Objects.requireNonNull(id, "id");
        this.code = normalize(code);
        this.name = Objects.requireNonNull(name, "name");
        this.parentId = parentId;
        this.description = description;
        this.createdAt = createdAt == null ? Instant.now() : createdAt;
        this.updatedAt = updatedAt == null ? this.createdAt : updatedAt;
        this.version = version;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new DomainException("Organization code is required");
        }
        return value.trim().toUpperCase();
    }

    public Organization rename(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new DomainException("Organization name is required");
        }
        return new Organization(id, code, newName.trim(), parentId, description, createdAt, Instant.now(), version);
    }

    public Organization recode(String newCode) {
        return new Organization(id, normalize(newCode), name, parentId, description, createdAt, Instant.now(), version);
    }

    public Organization describe(String newDescription) {
        return new Organization(id, code, name, parentId, newDescription, createdAt, Instant.now(), version);
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public UUID getParentId() {
        return parentId;
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
}
