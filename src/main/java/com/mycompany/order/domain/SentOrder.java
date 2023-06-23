package com.mycompany.order.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.Instant;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class SentOrder extends Order {

    private final Instant sentTime;
    private final ShipmentId shipmentId;

    public SentOrder(@NonNull AcceptedOrder order,
                     @NonNull Instant sentTime,
                     @NonNull ShipmentId shipmentId) {
        super(order.id(),
            order.createTime(),
            OrderStatus.SENT,
            order.customerId(),
            order.orderItems(),
            order.discount(),
            order.deliveryCost());
        this.sentTime = sentTime;
        this.shipmentId = shipmentId;
    }
}
