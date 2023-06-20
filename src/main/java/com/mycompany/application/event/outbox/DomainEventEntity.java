package com.mycompany.application.event.outbox;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "domain_events")
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
@Getter
@EqualsAndHashCode
@NamedQuery(name = "getAllDomainEvents", query = "select d from DomainEventEntity d")
final class DomainEventEntity {

    private @Id @JsonProperty UUID eventId;
    private @JsonProperty Instant eventTime;
    private @JsonProperty UUID correlationId;
    private @JsonProperty String name;
    @Type(JsonType.class)
    private @JsonProperty JsonNode data;
}
