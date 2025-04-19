package com.ecommerce.auth.enums;

public enum UserType {
    CUSTOMER("CUSTOMER"),
    ADMIN("ADMIN");

    private final String userType;

    UserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return userType;
    }
}
