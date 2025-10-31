package com.example.identity.domain.role;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("role_permissions")
public class RolePermission {

    @Column("permission_name")
    private final String permissionName;

    @Column("permission_description")
    private final String description;

    public RolePermission(String permissionName, String description) {
        Permission permission = new Permission(permissionName, description);
        this.permissionName = permission.getName();
        this.description = permission.getDescription();
    }

    public String getPermissionName() {
        return permissionName;
    }

    public String getDescription() {
        return description;
    }

    public Permission toPermission() {
        return new Permission(permissionName, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermission that = (RolePermission) o;
        return permissionName.equals(that.permissionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissionName);
    }
}
