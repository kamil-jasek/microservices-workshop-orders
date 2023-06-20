package com.mycompany.order.infra.port.inbound.kafka.warehouse;

import com.mycompany.application.event.DomainEvent;
import com.mycompany.application.event.DomainEventData;
import com.mycompany.application.json.JsonSubtype;
import lombok.NonNull;

import java.time.Instant;
import java.util.UUID;

import static com.mycompany.order.infra.port.inbound.kafka.warehouse.StockReleasedV1.StockReleased;

@JsonSubtype
final class StockReleasedV1 extends DomainEvent<StockReleased> {

    public StockReleasedV1(@NonNull UUID eventId,
                           @NonNull Instant eventTime,
                           UUID correlationId,
                           @NonNull StockReleased data) {
        super(eventId, eventTime, correlationId, data);
    }

    record StockReleased(UUID waybillId, UUID orderId) implements DomainEventData {
    }
}
