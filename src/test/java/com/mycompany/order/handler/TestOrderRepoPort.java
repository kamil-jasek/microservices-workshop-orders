package com.mycompany.order.handler;

import com.mycompany.order.domain.Order;
import com.mycompany.order.domain.OrderId;
import com.mycompany.order.infra.port.outbound.repo.OrderRepoPort;

import java.util.HashMap;
import java.util.Map;

final class TestOrderRepoPort implements OrderRepoPort {

    private final Map<OrderId, Order> repo = new HashMap<>();

    @Override
    public Order getOrder(OrderId orderId) {
        return repo.get(orderId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Order> T getOrderAs(OrderId orderId, Class<T> clazz) {
        return (T) getOrder(orderId);
    }

    @Override
    public void save(Order order) {
        repo.put(order.id(), order);
    }
}
