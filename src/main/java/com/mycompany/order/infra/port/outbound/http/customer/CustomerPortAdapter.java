package com.mycompany.order.infra.port.outbound.http.customer;

import com.mycompany.order.domain.CustomerId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class CustomerPortAdapter implements CustomerPort {

    final CustomerRestApi api;

    @Override
    public boolean customerExists(CustomerId customerId) {
        final var dto = api.findById(customerId.id());
        return dto != null && dto.id() != null;
    }
}
