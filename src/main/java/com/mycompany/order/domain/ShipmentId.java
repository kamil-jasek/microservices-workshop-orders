package com.mycompany.order.domain;

import lombok.NonNull;

import java.util.UUID;

public record ShipmentId(@NonNull UUID id) {
}
