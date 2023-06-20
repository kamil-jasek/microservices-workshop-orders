package com.mycompany.order.infra.port.inbound.http.write;

import java.time.Instant;
import java.util.UUID;

record OrderSentDto(UUID orderId, Instant sentTime) {
}
