package com.example.identity.infrastructure.persistence.entity;

import com.example.identity.domain.account.AccountStatus;
import com.example.identity.domain.account.UserAccount;
import com.example.identity.domain.account.UserIdentity;
import com.example.identity.domain.account.UserRoleAssignment;
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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Table("user_accounts")
public final class UserAccountEntity {

    @Id
    private final UUID id;

    private final String username;

    private final String email;

    private final String passwordHash;

    private final AccountStatus status;

    private final UUID organizationId;

    private final Instant createdAt;

    @LastModifiedDate
    private final Instant updatedAt;

    @Version
    private final Long version;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
    private final UserIdentityEmbeddable identity;

    @MappedCollection(idColumn = "user_account_id", keyColumn = "role_id")
    private final Set<UserRoleAssignmentEntity> roleAssignments;

    UserAccountEntity(UserAccount account) {
        this(
                account.getId(),
                account.getUsername(),
                account.getEmail().getValue(),
                account.getPasswordHash(),
                account.getStatus(),
                account.getOrganizationId(),
                account.getCreatedAt(),
                account.getUpdatedAt(),
                account.getVersion(),
                UserIdentityEmbeddable.fromDomain(account.getIdentity()),
                account.getRoleAssignments().stream()
                        .map(UserRoleAssignmentEntity::new)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

    @PersistenceCreator
    UserAccountEntity(UUID id,
                      String username,
                      String email,
                      String passwordHash,
                      AccountStatus status,
                      UUID organizationId,
                      Instant createdAt,
                      Instant updatedAt,
                      Long version,
                      UserIdentityEmbeddable identity,
                      Set<UserRoleAssignmentEntity> roleAssignments) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.status = status;
        this.organizationId = organizationId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
        this.identity = identity;
        this.roleAssignments = roleAssignments == null ? new LinkedHashSet<>() : new LinkedHashSet<>(roleAssignments);
    }

    UserAccount toDomain() {
        Set<UserRoleAssignment> assignments = roleAssignments.stream()
                .map(UserRoleAssignmentEntity::toDomain)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        UserIdentity domainIdentity = identity == null ? null : identity.toDomain();
        return new UserAccount(
                id,
                username,
                new EmailAddress(email),
                passwordHash,
                status,
                organizationId,
                createdAt,
                updatedAt,
                version,
                domainIdentity,
                assignments
        );
    }
}
