package com.example.identity.application.organization;

import com.example.identity.domain.organization.Organization;
import com.example.identity.domain.organization.OrganizationRepository;
import com.example.identity.domain.shared.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrganizationApplicationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationApplicationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Organization create(CreateOrganizationCommand command) {
        Organization organization = Organization.create(null, command.code(), command.name(), command.parentId(), command.description());
        if (organizationRepository.existsByCode(organization.getCode())) {
            throw new DomainException("Organization code already exists");
        }
        return organizationRepository.save(organization);
    }

    public Organization rename(RenameOrganizationCommand command) {
        Organization organization = requireOrganization(command.organizationId());
        Organization updated = organization.rename(command.name());
        return organizationRepository.save(updated);
    }

    public Organization recode(RecodeOrganizationCommand command) {
        Organization organization = requireOrganization(command.organizationId());
        Organization updated = organization.recode(command.code());
        if (!organization.getCode().equals(updated.getCode()) && organizationRepository.existsByCode(updated.getCode())) {
            throw new DomainException("Organization code already exists");
        }
        return organizationRepository.save(updated);
    }

    public Organization describe(DescribeOrganizationCommand command) {
        Organization organization = requireOrganization(command.organizationId());
        Organization updated = organization.describe(command.description());
        return organizationRepository.save(updated);
    }

    private Organization requireOrganization(java.util.UUID organizationId) {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new DomainException("Organization not found"));
    }
}
