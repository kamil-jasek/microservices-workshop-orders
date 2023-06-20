package com.mycompany.application.event.outbox;

import jakarta.persistence.EntityManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
class KafkaOutboxDomainEventPublisher {

    private final @NonNull EntityManager em;
    private final @NonNull KafkaTemplate<String, DomainEventEntity> kafkaTemplate;
    private final ConcurrentHashMap<String, String> topicNames = new ConcurrentHashMap<>();

    @Transactional
    void publish() {
        getGetAllDomainEvents().forEach(this::send);
    }

    private void send(DomainEventEntity event) {
        final var topicName = getTopicName(event.name());
        kafkaTemplate.send(topicName, event);
        em.remove(event);
    }

    private String getTopicName(String eventName) {
        return topicNames.computeIfAbsent(eventName, name -> "orders-" + name
            .replaceAll("(.)(\\p{Upper})", "$1-$2")
            .toLowerCase());
    }

    private List<DomainEventEntity> getGetAllDomainEvents() {
        return em
            .createNamedQuery("getAllDomainEvents", DomainEventEntity.class)
            .getResultList();
    }
}
