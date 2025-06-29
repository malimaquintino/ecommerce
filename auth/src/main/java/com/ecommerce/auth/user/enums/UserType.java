package com.ecommerce.auth.user.enums;

public enum UserType {
    CUSTOMER("CUSTOMER"),
    ADMIN("ADMIN"),
    STORE_ADMIN("STORE_ADMIN");

    private final String userType;

    UserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return userType;
    }
}
