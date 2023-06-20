package com.mycompany.order.infra.port.outbound.http.customer;

import com.mycompany.order.domain.CustomerId;

public interface CustomerPort {
    boolean customerExists(CustomerId customerId);
}
