package com.mycompany.order.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.Instant;

@Getter
@EqualsAndHashCode
@ToString
public abstract class Order {
    private final OrderId id;
    private final Instant createTime;
    private final OrderStatus status;
    private final CustomerId customerId;
    private final ExchangedOrderItemList orderItems;
    private final Price discount;
    private final Price deliveryCost;

    Order(@NonNull OrderId orderId,
          @NonNull Instant createTime,
          @NonNull OrderStatus status,
          @NonNull CustomerId customerId,
          @NonNull ExchangedOrderItemList orderItems,
          @NonNull Price discount,
          @NonNull Price deliveryCost) {
        this.id = orderId;
        this.createTime = createTime;
        this.status = status;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.discount = discount;
        this.deliveryCost = deliveryCost;
    }

    public Price totalPrice() {
        return orderItems.totalPrice().plus(deliveryCost);
    }

    public Price discountedTotalPrice() {
        return orderItems.totalPrice().minus(discount).plus(deliveryCost);
    }
}
