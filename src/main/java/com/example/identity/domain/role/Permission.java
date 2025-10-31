package com.example.identity.domain.role;

import java.util.Objects;

public class Permission {

    private final String name;
    private final String description;

    public Permission(String name, String description) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Permission name is required");
        }
        this.name = name.trim().toLowerCase();
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
