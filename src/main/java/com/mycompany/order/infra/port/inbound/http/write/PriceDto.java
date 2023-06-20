package com.mycompany.order.infra.port.inbound.http.write;

import jakarta.validation.constraints.Pattern;

record PriceDto(
    @Pattern(regexp = "\\d+\\.\\d{2}") String value,
    @Pattern(regexp = "USD|PLN|EUR") String currency) {
}
