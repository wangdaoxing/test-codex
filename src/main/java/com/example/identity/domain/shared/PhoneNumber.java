package com.example.identity.domain.shared;

import java.util.Objects;
import java.util.regex.Pattern;

public final class PhoneNumber {

    private static final Pattern PATTERN = Pattern.compile("^[+][0-9]{7,15}$");

    private final String value;

    public PhoneNumber(String value) {
        if (value == null || value.isBlank() || !PATTERN.matcher(value).matches()) {
            throw new DomainException("Invalid phone number: " + value);
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
