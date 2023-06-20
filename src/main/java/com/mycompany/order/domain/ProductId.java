package com.mycompany.order.domain;

import java.util.UUID;

import static com.mycompany.application.util.UUIDExtension.uuid;

public record ProductId(UUID id) {

    public static ProductId of(String uuid) {
        return new ProductId(uuid(uuid));
    }
}
