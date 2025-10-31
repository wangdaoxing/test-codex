package com.example.identity.domain.role;

import java.util.Objects;

public class RolePermission {

    private final String permissionName;

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
