package org.example.intro.enums;

public enum RoleName {
    ADMIN("ADMIN"),
    USER("USER");

    public final String name;

    RoleName(String roleName) {
        this.name = roleName;
    }
}
