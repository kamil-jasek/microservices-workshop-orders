package com.mycompany.order.infra.port.inbound.http.write;

import java.time.Instant;
import java.util.UUID;

record OrderCanceledDto(UUID orderId, Instant cancelTime) {
}
