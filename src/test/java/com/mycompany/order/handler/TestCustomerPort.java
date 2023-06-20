package com.mycompany.order.handler;

import com.mycompany.order.infra.port.outbound.http.customer.CustomerPort;
import com.mycompany.order.domain.CustomerId;

import java.util.List;

import static com.mycompany.application.util.UUIDExtension.uuid;

final class TestCustomerPort implements CustomerPort {

    private final List<CustomerId> existingCustomers = List.of(
        new CustomerId(uuid("9c74dd58-83e3-4a21-8220-bec2ddcd590a"))
    );

    @Override
    public boolean customerExists(CustomerId customerId) {
        return existingCustomers.contains(customerId);
    }
}
