package com.mycompany.order.event;

import com.mycompany.application.event.DomainEvent;
import com.mycompany.application.json.JsonSubtype;
import lombok.NonNull;

import java.time.Instant;
import java.util.UUID;

@JsonSubtype
public final class OrderSentV1 extends DomainEvent<OrderDataV1> {

    public OrderSentV1(@NonNull UUID eventId,
                       @NonNull Instant eventTime,
                       UUID correlationId,
                       @NonNull OrderDataV1 data) {
        super(eventId, eventTime, correlationId, data);
    }
}
