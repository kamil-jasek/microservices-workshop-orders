package com.mycompany.order.infra.port.inbound.http.write;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

record SendOrderRequest(@NotNull UUID shipmentId) {
}
