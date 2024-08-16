package org.example.intro.enums;

public enum OrderStatus {
    PENDING("PENDING"),
    DELIVERED("DELIVERED"),
    COMPLETED("COMPLETED");

    public final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
