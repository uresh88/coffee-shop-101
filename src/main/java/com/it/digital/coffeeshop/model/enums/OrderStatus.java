package com.it.digital.coffeeshop.model.enums;

public enum OrderStatus {
    WAITING("w"), PREPAIRING("p"), DONE("d");

    private final String status;
    OrderStatus(String status){
        this.status = status;
    }

}
