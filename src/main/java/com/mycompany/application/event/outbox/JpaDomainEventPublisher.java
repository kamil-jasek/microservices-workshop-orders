package com.mycompany.application.event.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.application.event.DomainEvent;
import com.mycompany.application.event.DomainEventPublisher;
import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@RequiredArgsConstructor
class JpaDomainEventPublisher implements DomainEventPublisher {

    private final @NonNull EntityManager em;
    private final @NonNull ObjectMapper eventMapper;

    @Transactional(propagation = MANDATORY)
    @Override
    public void publish(DomainEvent<?> event) {
        em.persist(new DomainEventEntity(
            event.eventId(),
            event.eventTime(),
            event.correlationId(),
            event.getClass().getSimpleName(),
            eventMapper.valueToTree(event.data())
        ));
    }
}
