package com.mycompany.order.event;

import com.mycompany.application.event.DomainEventData;
import com.mycompany.order.domain.AcceptedOrder;
import com.mycompany.order.domain.CanceledOrder;
import com.mycompany.order.domain.ExchangedOrderItemList;
import com.mycompany.order.domain.SentOrder;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public record OrderDataV1(
    @NonNull UUID id,
    @NonNull UUID customerId,
    @NonNull Instant createTime,
    @NonNull String status,
    @NonNull String currency,
    @NonNull List<OrderItemData> orderItems,
    @NonNull BigDecimal discount,
    @NonNull BigDecimal deliveryCost,
    Instant sentTime,
    Instant cancelTime,
    String cancelReason
) implements DomainEventData {

    public record OrderItemData(
        @NonNull UUID productId,
        @NonNull BigDecimal originalPrice,
        @NonNull String originalCurrency,
        @NonNull BigDecimal exchangedPrice,
        double weight,
        @NonNull String weightUnit,
        int quantity
    ) {
    }

    public static OrderDataV1 from(AcceptedOrder order) {
        return new OrderDataV1(
            order.id().id(),
            order.customerId().id(),
            order.createTime(),
            order.status().name(),
            order.orderItems().baseCurrency().name(),
            collectOrderItems(order.orderItems()),
            order.discount().value(),
            order.deliveryCost().value(),
            null,
            null,
            null
        );
    }

    public static OrderDataV1 from(SentOrder order) {
        return new OrderDataV1(
            order.id().id(),
            order.customerId().id(),
            order.createTime(),
            order.status().name(),
            order.orderItems().baseCurrency().name(),
            collectOrderItems(order.orderItems()),
            order.discount().value(),
            order.deliveryCost().value(),
            order.sentTime(),
            null,
            null
        );
    }

    public static OrderDataV1 from(CanceledOrder order) {
        return new OrderDataV1(
            order.id().id(),
            order.customerId().id(),
            order.createTime(),
            order.status().name(),
            order.orderItems().baseCurrency().name(),
            collectOrderItems(order.orderItems()),
            order.discount().value(),
            order.deliveryCost().value(),
            null,
            order.cancelTime(),
            order.reason()
        );
    }

    private static List<OrderItemData> collectOrderItems(ExchangedOrderItemList itemList) {
        return itemList.orderItems()
            .stream()
            .map(item -> new OrderItemData(
                item.orderItem().productId().id(),
                item.orderItem().price().value(),
                item.orderItem().price().currency().name(),
                item.exchangedPrice().value(),
                item.orderItem().weight().value(),
                item.orderItem().weight().unit().name(),
                item.orderItem().quantity().value()))
            .collect(toList());
    }
}
