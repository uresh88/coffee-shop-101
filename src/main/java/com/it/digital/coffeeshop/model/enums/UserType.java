package com.it.digital.coffeeshop.model.enums;

public enum UserType {
    ADMIN_USER("adm"),
    CORPORATE_USER("cop"),
    CUSTOMER("cus");

    private final String val;
    UserType(String cus) {
        this.val = cus;
    }
}
