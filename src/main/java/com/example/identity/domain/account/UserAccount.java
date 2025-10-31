package com.example.identity.domain.account;

import com.example.identity.domain.shared.DomainException;
import com.example.identity.domain.shared.EmailAddress;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Table("user_accounts")
public class UserAccount {

    @Id
    private final UUID id;

    private final String username;

    private final EmailAddress email;

    private final String passwordHash;

    private final AccountStatus status;

    private final UUID organizationId;

    private final Instant createdAt;

    @LastModifiedDate
    private final Instant updatedAt;

    @Version
    private final Long version;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private final UserIdentity identity;

    @MappedCollection(idColumn = "user_account_id")
    private final Set<UserRoleAssignment> roleAssignments;

    public static UserAccount register(UUID id,
                                       String username,
                                       EmailAddress email,
                                       String passwordHash,
                                       UUID organizationId,
                                       UserIdentity identity) {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (username == null || username.isBlank()) {
            throw new DomainException("Username is required");
        }
        if (email == null) {
            throw new DomainException("Email is required");
        }
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new DomainException("Password hash is required");
        }
        if (organizationId == null) {
            throw new DomainException("Organization id is required");
        }
        return new UserAccount(id, username.trim().toLowerCase(), email, passwordHash, AccountStatus.ACTIVE,
                organizationId, Instant.now(), null, null, identity, new LinkedHashSet<>());
    }

    @PersistenceCreator
    public UserAccount(UUID id,
                       String username,
                       EmailAddress email,
                       String passwordHash,
                       AccountStatus status,
                       UUID organizationId,
                       Instant createdAt,
                       Instant updatedAt,
                       Long version,
                       UserIdentity identity,
                       Set<UserRoleAssignment> roleAssignments) {
        this.id = Objects.requireNonNull(id, "id");
        this.username = Objects.requireNonNull(username, "username");
        this.email = Objects.requireNonNull(email, "email");
        this.passwordHash = Objects.requireNonNull(passwordHash, "passwordHash");
        this.status = status == null ? AccountStatus.ACTIVE : status;
        this.organizationId = Objects.requireNonNull(organizationId, "organizationId");
        this.createdAt = createdAt == null ? Instant.now() : createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
        this.identity = identity;
        this.roleAssignments = roleAssignments == null ? new LinkedHashSet<>() : new LinkedHashSet<>(roleAssignments);
    }

    public UserAccount assignRole(UUID roleId, String roleCode) {
        if (roleAssignments.stream().anyMatch(assignment -> assignment.getRoleId().equals(roleId))) {
            throw new DomainException("Role already assigned to user");
        }
        UserAccount copy = cloneForMutation();
        copy.roleAssignments.add(new UserRoleAssignment(roleId, roleCode));
        return copy;
    }

    public UserAccount revokeRole(UUID roleId) {
        UserAccount copy = cloneForMutation();
        boolean removed = copy.roleAssignments.removeIf(assignment -> assignment.getRoleId().equals(roleId));
        if (!removed) {
            throw new DomainException("Role was not assigned to user");
        }
        return copy;
    }

    public UserAccount changeEmail(EmailAddress newEmail) {
        return new UserAccount(id, username, newEmail, passwordHash, status, organizationId, createdAt, Instant.now(), version,
                identity, new LinkedHashSet<>(roleAssignments));
    }

    public UserAccount lock() {
        return withStatus(AccountStatus.LOCKED);
    }

    public UserAccount disable() {
        return withStatus(AccountStatus.DISABLED);
    }

    public UserAccount activate() {
        return withStatus(AccountStatus.ACTIVE);
    }

    private UserAccount withStatus(AccountStatus newStatus) {
        return new UserAccount(id, username, email, passwordHash, newStatus, organizationId, createdAt, Instant.now(), version,
                identity, new LinkedHashSet<>(roleAssignments));
    }

    private UserAccount cloneForMutation() {
        return new UserAccount(id, username, email, passwordHash, status, organizationId, createdAt, Instant.now(), version, identity, new LinkedHashSet<>(roleAssignments));
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public UUID getOrganizationId() {
        return organizationId;
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

    public UserIdentity getIdentity() {
        return identity;
    }

    public Set<UserRoleAssignment> getRoleAssignments() {
        return Set.copyOf(roleAssignments);
    }
}
