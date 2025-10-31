package com.example.identity.domain.account;

import com.example.identity.domain.shared.FullName;
import com.example.identity.domain.shared.PhoneNumber;

import java.util.Optional;

public class UserIdentity {

    private final String givenName;
    private final String familyName;
    private final String phoneNumber;

    public UserIdentity(FullName fullName, PhoneNumber phoneNumber) {
        this(fullName.getGivenName(), fullName.getFamilyName(), phoneNumber != null ? phoneNumber.getValue() : null);
    }

    public UserIdentity(String givenName, String familyName) {
        this(givenName, familyName, null);
    }

    public UserIdentity(String givenName, String familyName, String phoneNumber) {
        FullName name = new FullName(givenName, familyName);
        this.givenName = name.getGivenName();
        this.familyName = name.getFamilyName();
        if (phoneNumber != null) {
            this.phoneNumber = new PhoneNumber(phoneNumber).getValue();
        } else {
            this.phoneNumber = null;
        }
    }

    public FullName getFullName() {
        return new FullName(givenName, familyName);
    }

    public Optional<PhoneNumber> getPhoneNumber() {
        return phoneNumber == null ? Optional.empty() : Optional.of(new PhoneNumber(phoneNumber));
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getDisplayName() {
        return getFullName().getDisplayName();
    }

    public String getPhoneNumberValue() {
        return phoneNumber;
    }
}
