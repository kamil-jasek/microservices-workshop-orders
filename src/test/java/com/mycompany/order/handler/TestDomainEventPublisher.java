package com.mycompany.order.handler;

import com.mycompany.application.event.DomainEvent;
import com.mycompany.application.event.DomainEventPublisher;

import java.util.ArrayList;
import java.util.List;

final class TestDomainEventPublisher implements DomainEventPublisher {

    private final List<DomainEvent<?>> publishedEvents = new ArrayList<>();

    @Override
    public void publish(DomainEvent<?> event) {
        publishedEvents.add(event);
    }

    public boolean hasPublished(Class<? extends DomainEvent<?>> eventDataClass) {
        return publishedEvents
            .stream()
            .anyMatch(domainEvent -> domainEvent
                .getClass()
                .isAssignableFrom(eventDataClass));
    }
}
