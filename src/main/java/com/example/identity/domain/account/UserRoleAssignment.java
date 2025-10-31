package com.example.identity.domain.account;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Table("user_role_assignments")
public class UserRoleAssignment {

    @Column("role_id")
    private final UUID roleId;

    @Column("role_code")
    private final String roleCode;

    @Column("assigned_at")
    private final Instant assignedAt;

    public UserRoleAssignment(UUID roleId, String roleCode) {
        this(roleId, roleCode, Instant.now());
    }

    public UserRoleAssignment(UUID roleId, String roleCode, Instant assignedAt) {
        if (roleId == null) {
            throw new IllegalArgumentException("Role id is required");
        }
        if (roleCode == null || roleCode.isBlank()) {
            throw new IllegalArgumentException("Role code is required");
        }
        this.roleId = roleId;
        this.roleCode = roleCode;
        this.assignedAt = assignedAt == null ? Instant.now() : assignedAt;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public Instant getAssignedAt() {
        return assignedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleAssignment that = (UserRoleAssignment) o;
        return roleId.equals(that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId);
    }
}
