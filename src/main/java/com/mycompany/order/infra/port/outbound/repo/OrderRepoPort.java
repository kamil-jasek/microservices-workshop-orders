package com.mycompany.order.infra.port.outbound.repo;

import com.mycompany.order.domain.*;

public interface OrderRepoPort {
    Order getOrder(OrderId orderId);

    <T extends Order> T getOrderAs(OrderId orderId, Class<T> clazz);

    void save(Order order);
}
