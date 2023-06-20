package com.mycompany.order.infra.port.outbound.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface OrderEntityRepo extends JpaRepository<OrderEntity, UUID> {
}
