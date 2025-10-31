package com.example.identity.application.role;

import com.example.identity.domain.organization.OrganizationRepository;
import com.example.identity.domain.role.Role;
import com.example.identity.domain.role.RoleRepository;
import com.example.identity.domain.shared.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleApplicationService {

    private final RoleRepository roleRepository;
    private final OrganizationRepository organizationRepository;

    public RoleApplicationService(RoleRepository roleRepository, OrganizationRepository organizationRepository) {
        this.roleRepository = roleRepository;
        this.organizationRepository = organizationRepository;
    }

    public Role create(CreateRoleCommand command) {
        organizationRepository.findById(command.organizationId())
                .orElseThrow(() -> new DomainException("Organization not found"));
        Role role = Role.create(null, command.organizationId(), command.code(), command.name(), command.description());
        if (roleRepository.existsByCode(command.organizationId(), role.getCode())) {
            throw new DomainException("Role code already exists in organization");
        }
        Role enriched = command.permissions() == null ? role : command.permissions().stream()
                .reduce(role, (acc, permission) -> acc.grantPermission(permission.name(), permission.description()),
                        (left, right) -> right);
        return roleRepository.save(enriched);
    }

    public Role rename(RenameRoleCommand command) {
        Role role = requireRole(command.roleId());
        Role updated = role.rename(command.name());
        return roleRepository.save(updated);
    }

    public Role describe(DescribeRoleCommand command) {
        Role role = requireRole(command.roleId());
        Role updated = role.describe(command.description());
        return roleRepository.save(updated);
    }

    public Role grantPermission(GrantPermissionToRoleCommand command) {
        Role role = requireRole(command.roleId());
        Role updated = role.grantPermission(command.permission().name(), command.permission().description());
        return roleRepository.save(updated);
    }

    public Role revokePermission(RevokePermissionFromRoleCommand command) {
        Role role = requireRole(command.roleId());
        Role updated = role.revokePermission(command.permissionName());
        return roleRepository.save(updated);
    }

    private Role requireRole(java.util.UUID roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new DomainException("Role not found"));
    }
}
