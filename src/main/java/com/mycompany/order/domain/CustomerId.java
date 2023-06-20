package com.mycompany.order.domain;

import java.util.UUID;

import static com.mycompany.application.util.UUIDExtension.uuid;

public record CustomerId(UUID id) {
    public static CustomerId of(String name) {
        return new CustomerId(uuid(name));
    }
}
