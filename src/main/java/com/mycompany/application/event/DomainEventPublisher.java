package com.mycompany.application.event;

public interface DomainEventPublisher {

    void publish(DomainEvent<?> event);
}
