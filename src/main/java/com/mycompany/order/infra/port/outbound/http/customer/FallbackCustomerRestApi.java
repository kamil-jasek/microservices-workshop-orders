package com.mycompany.order.infra.port.outbound.http.customer;

import java.util.Set;
import java.util.UUID;

final class FallbackCustomerRestApi implements CustomerRestApi {

    private final Set<UUID> existingCustomers = Set.of(
        UUID.fromString("a13dfdb8-6e30-4d31-99e4-62c3a308e310")
    );

    @Override
    public CustomerDto findById(UUID id) {
        if (existingCustomers.contains(id)) {
            return new CustomerDto(id);
        }
        throw new IllegalStateException("cannot verify customer with id: " + id);
    }
}
