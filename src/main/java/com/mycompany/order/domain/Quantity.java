package com.mycompany.order.domain;

public record Quantity(int value) {
    public static Quantity of(int value) {
        return new Quantity(value);
    }
}
