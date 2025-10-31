package com.example.identity.infrastructure.persistence.entity;

import com.example.identity.domain.role.RolePermission;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("role_permissions")
final class RolePermissionEntity {

    @Column("permission_name")
    private final String permissionName;

    @Column("permission_description")
    private final String permissionDescription;

    RolePermissionEntity(RolePermission permission) {
        this(permission.getPermissionName(), permission.getDescription());
    }

    @PersistenceCreator
    RolePermissionEntity(String permissionName, String permissionDescription) {
        this.permissionName = permissionName;
        this.permissionDescription = permissionDescription;
    }

    RolePermission toDomain() {
        return new RolePermission(permissionName, permissionDescription);
    }

    String getPermissionName() {
        return permissionName;
    }
}
