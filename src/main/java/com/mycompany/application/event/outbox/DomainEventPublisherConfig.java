package com.mycompany.application.event.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.application.event.DomainEventPublisher;
import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
@EntityScan
class DomainEventPublisherConfig {

    private final ObjectMapper mapper = new ObjectMapper()
        .configure(WRITE_DATES_AS_TIMESTAMPS, false)
        .findAndRegisterModules();

    @Bean
    DomainEventPublisher domainEventPublisher(EntityManager em) {
        return new JpaDomainEventPublisher(em, mapper);
    }

    @Bean
    KafkaOutboxDomainEventPublisher kafkaOutboxDomainEventPublisher(
        EntityManager em,
        KafkaTemplate<String, DomainEventEntity> kafkaTemplate) {
        return new KafkaOutboxDomainEventPublisher(em, kafkaTemplate);
    }

    @Bean
    DomainEventPublisherJob domainEventPublisherJob(KafkaOutboxDomainEventPublisher publisher) {
        return new DomainEventPublisherJob(publisher);
    }
}
