package com.example.identity.infrastructure.persistence.entity;

import com.example.identity.domain.account.UserRoleAssignment;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("user_role_assignments")
final class UserRoleAssignmentEntity {

    @Column("role_id")
    private final UUID roleId;

    @Column("role_code")
    private final String roleCode;

    @Column("assigned_at")
    private final Instant assignedAt;

    UserRoleAssignmentEntity(UserRoleAssignment assignment) {
        this(assignment.getRoleId(), assignment.getRoleCode(), assignment.getAssignedAt());
    }

    @PersistenceCreator
    UserRoleAssignmentEntity(UUID roleId, String roleCode, Instant assignedAt) {
        this.roleId = roleId;
        this.roleCode = roleCode;
        this.assignedAt = assignedAt;
    }

    UserRoleAssignment toDomain() {
        return new UserRoleAssignment(roleId, roleCode, assignedAt);
    }

    UUID getRoleId() {
        return roleId;
    }
}
