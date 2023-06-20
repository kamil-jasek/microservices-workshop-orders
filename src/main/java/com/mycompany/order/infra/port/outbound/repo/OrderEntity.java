package com.mycompany.order.infra.port.outbound.repo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "orders")
@NoArgsConstructor // for JPA only
@AllArgsConstructor
@Getter
class OrderEntity {

    @Id
    private UUID id;
    private UUID customerId;
    private Instant createTime;
    private String status;
    private String currency;
    @OneToMany(cascade = ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItemEntity> orderItems;
    private BigDecimal discount;
    private BigDecimal deliveryCost;
    private Instant sentTime;
    private Instant cancelTime;
    private String cancelReason;
}
