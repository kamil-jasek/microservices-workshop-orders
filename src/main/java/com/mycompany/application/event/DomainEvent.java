package com.mycompany.application.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mycompany.order.event.OrderCanceledV1;
import com.mycompany.order.event.OrderMadeV1;
import com.mycompany.order.event.OrderSentV1;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@JsonTypeInfo(use = NAME, property = "name")
//@JsonSubTypes({
//    @Type(value = OrderMadeV1.class, name = "OrderMadeV1"),
//    @Type(value = OrderSentV1.class, name = "OrderSentV1"),
//    @Type(value = OrderCanceledV1.class, name = "OrderCanceledV1"),
//})
public abstract class DomainEvent<T extends DomainEventData> {
    private final @NonNull UUID eventId;
    private final @NonNull Instant eventTime;
    private final UUID correlationId;
    private final @NonNull T data;
}
