package com.example.identity.domain.shared;

import java.util.Objects;

public final class FullName {

    private final String givenName;
    private final String familyName;

    public FullName(String givenName, String familyName) {
        if (givenName == null || givenName.isBlank()) {
            throw new DomainException("Given name is required");
        }
        if (familyName == null || familyName.isBlank()) {
            throw new DomainException("Family name is required");
        }
        this.givenName = givenName.trim();
        this.familyName = familyName.trim();
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getDisplayName() {
        return givenName + " " + familyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName = (FullName) o;
        return givenName.equals(fullName.givenName) && familyName.equals(fullName.familyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(givenName, familyName);
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
