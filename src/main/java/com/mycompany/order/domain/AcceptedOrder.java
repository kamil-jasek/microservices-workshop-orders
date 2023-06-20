package com.mycompany.order.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.Instant;

import static com.mycompany.order.domain.OrderStatus.ACCEPTED;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
public final class AcceptedOrder extends Order {

    public AcceptedOrder(@NonNull OrderId orderId,
                         @NonNull Instant createTime,
                         @NonNull CustomerId customerId,
                         @NonNull ExchangedOrderItemList orderItems,
                         @NonNull Price deliveryCost,
                         @NonNull Price discount) {
        super(orderId, createTime, ACCEPTED, customerId, orderItems, discount, deliveryCost);
    }

    public SentOrder sentAt(Instant sentTime) {
        return new SentOrder(this, sentTime);
    }

    public CanceledOrder cancel(Instant cancelTime, String reason) {
        return new CanceledOrder(this, cancelTime, reason);
    }
}
