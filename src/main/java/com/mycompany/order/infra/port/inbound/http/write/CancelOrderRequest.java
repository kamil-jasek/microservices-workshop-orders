package com.mycompany.order.infra.port.inbound.http.write;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

record CancelOrderRequest(@NotNull @NotBlank String reason) {
}
