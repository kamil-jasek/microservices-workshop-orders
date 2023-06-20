package com.mycompany.order.infra.port.outbound.repo;

import com.mycompany.order.domain.*;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

final class OrderEntityMapper {

    OrderEntity toEntity(AcceptedOrder order) {
        return new OrderEntity(
            order.id().id(),
            order.customerId().id(),
            order.createTime(),
            order.status().name(),
            order.orderItems().baseCurrency().name(),
            mapOrderItemsToEntities(order),
            order.discount().value(),
            order.deliveryCost().value(),
            null,
            null,
            null);
    }

    OrderEntity toEntity(SentOrder order) {
        return new OrderEntity(
            order.id().id(),
            order.customerId().id(),
            order.createTime(),
            order.status().name(),
            order.orderItems().baseCurrency().name(),
            mapOrderItemsToEntities(order),
            order.discount().value(),
            order.deliveryCost().value(),
            order.sentTime(),
            null,
            null);
    }

    OrderEntity toEntity(CanceledOrder order) {
        return new OrderEntity(
            order.id().id(),
            order.customerId().id(),
            order.createTime(),
            order.status().name(),
            order.orderItems().baseCurrency().name(),
            mapOrderItemsToEntities(order),
            order.discount().value(),
            order.deliveryCost().value(),
            null,
            order.cancelTime(),
            order.reason());
    }

    private static List<OrderItemEntity> mapOrderItemsToEntities(Order order) {
        return order.orderItems()
            .orderItems()
            .stream()
            .map(item -> new OrderItemEntity(
                UUID.randomUUID(),
                item.orderItem().productId().id(),
                item.orderItem().price().value(),
                item.orderItem().price().currency().name(),
                item.exchangedPrice().value(),
                item.orderItem().weight().value(),
                item.orderItem().weight().unit().name(),
                item.orderItem().quantity().value()))
            .collect(toList());
    }

    Order toDomain(OrderEntity entity) {
        if (entity.status().equals(OrderStatus.ACCEPTED.name())) {
            return toAcceptedOrder(entity);
        } else if (entity.status().equals(OrderStatus.SENT.name())) {
            return toSentOrder(entity);
        } else if (entity.status().equals(OrderStatus.CANCELED.name())) {
            return toCanceledOrder(entity);
        } else {
            throw new IllegalStateException(
                "unknown status of order: " + entity.status() + ", id: " + entity.id());
        }
    }

    private AcceptedOrder toAcceptedOrder(OrderEntity entity) {
        final var orderCurrency = Currency.valueOf(entity.currency());
        return new AcceptedOrder(
            new OrderId(entity.id()),
            entity.createTime(),
            new CustomerId(entity.customerId()),
            new ExchangedOrderItemList(
                orderCurrency,
                mapOrderItemEntitiesToDomain(entity.orderItems(), orderCurrency)
            ),
            new Price(entity.deliveryCost(), orderCurrency),
            new Price(entity.discount(), orderCurrency));
    }

    private Order toSentOrder(OrderEntity entity) {
        return new SentOrder(toAcceptedOrder(entity), entity.sentTime());
    }

    private Order toCanceledOrder(OrderEntity entity) {
        return new CanceledOrder(toAcceptedOrder(entity),
            entity.cancelTime(),
            entity.cancelReason());
    }

    private ExchangedOrderItem[] mapOrderItemEntitiesToDomain(List<OrderItemEntity> orderItems,
                                                              Currency orderCurrency) {
        return orderItems
            .stream()
            .map(item -> new ExchangedOrderItem(
                new OrderItem(
                    new ProductId(item.productId()),
                    new Price(item.originalPrice(), Currency.valueOf(item.originalCurrency())),
                    new Weight(item.weight(), WeightUnit.valueOf(item.weightUnit())),
                    Quantity.of(item.quantity())
                ),
                new Price(item.exchangedPrice(), orderCurrency)))
            .toArray(ExchangedOrderItem[]::new);
    }
}
