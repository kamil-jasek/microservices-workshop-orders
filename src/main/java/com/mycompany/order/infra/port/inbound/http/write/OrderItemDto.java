package com.mycompany.order.infra.port.inbound.http.write;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

record OrderItemDto(
    @NotNull @UUID
    String productId,
    @NotNull @Valid
    PriceDto price,
    @NotNull @Valid
    WeightDto weight,
    @Min(1)
    int quantity
) {
}
