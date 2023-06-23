package com.mycompany.order.infra.port.inbound.http.read;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.application.rest.hateoas.HateoasLink;
import com.mycompany.application.rest.hateoas.HateoasLinks;
import com.mycompany.application.rest.hateoas.HateoasResource;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Immutable
@Table(name = "orders")
@NoArgsConstructor // for JPA only
@AllArgsConstructor
@Getter(onMethod = @__(@JsonProperty))
final class OrderDto implements HateoasResource {

    @Id
    private UUID id;
    private UUID customerId;
    private Instant createTime;
    private String status;
    private String currency;
    private BigDecimal discount;
    private BigDecimal deliveryCost;
    private Instant sentTime;
    private UUID shipmentId;
    private Instant cancelTime;
    private String cancelReason;

    @Override
    public HateoasLinks links() {
        return new HateoasLinks(Map.of(
            "self", HateoasLink.of("/v1/orders/" + id),
            "items", HateoasLink.of("/v1/orders/" + id + "/items")
        ));
    }
}
