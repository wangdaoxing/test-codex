package com.example.identity.application.account;

import com.example.identity.domain.account.AccountStatus;
import com.example.identity.domain.account.UserAccount;
import com.example.identity.domain.account.UserAccountRepository;
import com.example.identity.domain.account.UserIdentity;
import com.example.identity.domain.organization.OrganizationRepository;
import com.example.identity.domain.role.Role;
import com.example.identity.domain.role.RoleRepository;
import com.example.identity.domain.shared.DomainException;
import com.example.identity.domain.shared.EmailAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AccountApplicationService {

    private final UserAccountRepository userAccountRepository;
    private final OrganizationRepository organizationRepository;
    private final RoleRepository roleRepository;

    public AccountApplicationService(UserAccountRepository userAccountRepository,
                                     OrganizationRepository organizationRepository,
                                     RoleRepository roleRepository) {
        this.userAccountRepository = userAccountRepository;
        this.organizationRepository = organizationRepository;
        this.roleRepository = roleRepository;
    }

    public UserAccount register(RegisterUserAccountCommand command) {
        requireOrganizationExists(command.organizationId());
        String normalizedUsername = command.username() == null ? null : command.username().trim().toLowerCase();
        if (normalizedUsername == null || normalizedUsername.isBlank()) {
            throw new DomainException("Username is required");
        }
        if (userAccountRepository.existsByUsername(normalizedUsername)) {
            throw new DomainException("Username already exists");
        }
        EmailAddress emailAddress = new EmailAddress(command.email());
        if (userAccountRepository.existsByEmail(emailAddress)) {
            throw new DomainException("Email already exists");
        }
        if (command.passwordHash() == null || command.passwordHash().isBlank()) {
            throw new DomainException("Password hash is required");
        }
        UserIdentity identity = new UserIdentity(command.givenName(), command.familyName(), command.phoneNumber());
        UserAccount account = UserAccount.register(UUID.randomUUID(), normalizedUsername, emailAddress, command.passwordHash(),
                command.organizationId(), identity);
        return userAccountRepository.save(account);
    }

    public UserAccount assignRole(AssignRoleToAccountCommand command) {
        UserAccount account = userAccountRepository.findById(command.accountId())
                .orElseThrow(() -> new DomainException("User account not found"));
        Role role = roleRepository.findById(command.roleId())
                .orElseThrow(() -> new DomainException("Role not found"));
        if (!role.getOrganizationId().equals(account.getOrganizationId())) {
            throw new DomainException("Role does not belong to the same organization as the user");
        }
        UserAccount updated = account.assignRole(role.getId(), role.getCode());
        return userAccountRepository.save(updated);
    }

    public UserAccount revokeRole(RevokeRoleFromAccountCommand command) {
        UserAccount account = userAccountRepository.findById(command.accountId())
                .orElseThrow(() -> new DomainException("User account not found"));
        UserAccount updated = account.revokeRole(command.roleId());
        return userAccountRepository.save(updated);
    }

    public UserAccount changeEmail(ChangeEmailCommand command) {
        UserAccount account = userAccountRepository.findById(command.accountId())
                .orElseThrow(() -> new DomainException("User account not found"));
        EmailAddress newEmail = new EmailAddress(command.newEmail());
        if (!account.getEmail().equals(newEmail) && userAccountRepository.existsByEmail(newEmail)) {
            throw new DomainException("Email already exists");
        }
        UserAccount updated = account.changeEmail(newEmail);
        return userAccountRepository.save(updated);
    }

    public UserAccount changeStatus(UUID accountId, AccountStatus status) {
        if (status == null) {
            throw new DomainException("Account status is required");
        }
        UserAccount account = userAccountRepository.findById(accountId)
                .orElseThrow(() -> new DomainException("User account not found"));
        UserAccount updated = switch (status) {
            case ACTIVE -> account.activate();
            case LOCKED -> account.lock();
            case DISABLED -> account.disable();
        };
        return userAccountRepository.save(updated);
    }

    private void requireOrganizationExists(UUID organizationId) {
        organizationRepository.findById(organizationId)
                .orElseThrow(() -> new DomainException("Organization not found"));
    }
}
