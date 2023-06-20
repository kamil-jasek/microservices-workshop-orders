package com.mycompany.order.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.Instant;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CanceledOrder extends Order {

    private final Instant cancelTime;
    private final String reason;

    public CanceledOrder(@NonNull AcceptedOrder order,
                         @NonNull Instant cancelTime,
                         @NonNull String reason) {
        super(order.id(),
            order.createTime(),
            OrderStatus.CANCELED,
            order.customerId(),
            order.orderItems(),
            order.discount(),
            order.deliveryCost());
        this.cancelTime = cancelTime;
        this.reason = reason;
    }
}
