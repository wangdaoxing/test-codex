package com.example.identity.domain.account;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class UserRoleAssignment {

    private final UUID roleId;

    private final String roleCode;

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
