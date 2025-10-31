package com.example.identity.infrastructure.persistence.entity;

import com.example.identity.domain.account.UserIdentity;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;

final class UserIdentityEmbeddable {

    @Column("given_name")
    private final String givenName;

    @Column("family_name")
    private final String familyName;

    @Column("phone_number")
    private final String phoneNumber;

    UserIdentityEmbeddable(UserIdentity identity) {
        this(identity.getGivenName(), identity.getFamilyName(), identity.getPhoneNumberValue());
    }

    @PersistenceCreator
    UserIdentityEmbeddable(String givenName, String familyName, String phoneNumber) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.phoneNumber = phoneNumber;
    }

    UserIdentity toDomain() {
        return new UserIdentity(givenName, familyName, phoneNumber);
    }

    static UserIdentityEmbeddable fromDomain(UserIdentity identity) {
        if (identity == null) {
            return null;
        }
        return new UserIdentityEmbeddable(identity);
    }
}
