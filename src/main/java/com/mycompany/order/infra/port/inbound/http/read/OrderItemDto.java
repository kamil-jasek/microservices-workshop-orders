package com.mycompany.order.infra.port.inbound.http.read;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.application.rest.hateoas.HateoasResource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Immutable
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor // for JPA
@Getter(onMethod = @__(@JsonProperty))
@NamedQuery(
    name = "getByOrderId",
    query = "select o from OrderItemDto o where o.orderId = :orderId"
)
final class OrderItemDto implements HateoasResource {

    @Id
    private UUID id;
    @JsonIgnore
    @Column(name = "order_id")
    private UUID orderId;
    private UUID productId;
    private BigDecimal originalPrice;
    private String originalCurrency;
    private BigDecimal exchangedPrice;
    private double weight;
    private String weightUnit;
    private int quantity;
}
