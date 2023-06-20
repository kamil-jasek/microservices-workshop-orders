package com.mycompany.order.infra.port.inbound.http.read;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.UUID;

record OrderFilter(
    UUID customerId,
    @Pattern(regexp = "ACCEPTED|SENT|CANCELED") String status,
    @Pattern(regexp = "ASC|DESC") String sortOrder
) {

    Predicate[] predicates(CriteriaBuilder cb, Root<OrderDto> root) {
        final var predicates = new ArrayList<Predicate>();
        if (customerId != null) {
            predicates.add(cb.equal(root.get("customerId"), customerId));
        }
        if (status != null && !status.isBlank()) {
            predicates.add(cb.equal(root.get("status"), status));
        }
        return predicates.toArray(new Predicate[0]);
    }
}
