package com.mycompany.order.infra.port.inbound.http.read;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
final class OrderView {

    @NonNull
    final EntityManager em;

    List<OrderDto> filter(OrderFilter filter) {
        final var cb = em.getCriteriaBuilder();
        final var query = cb.createQuery(OrderDto.class);
        final var root = query.from(OrderDto.class);
        query.select(root);
        Predicate[] predicates = filter.predicates(cb, root);
        if (predicates.length > 0) {
            query.where(predicates);
        }
        if (filter.sortOrder() != null) {
            final var createTime = root.get("createTime");
            query.orderBy(filter.sortOrder().equals("ASC")
                ? cb.asc(createTime)
                : cb.desc(createTime));
        }
        return em.unwrap(Session.class).createQuery(query).getResultList();
    }

    public OrderDto getById(UUID id) {
        return em.getReference(OrderDto.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<OrderItemDto> getOrderItems(UUID orderId) {
        final var items = em.createNamedQuery("getByOrderId")
            .setParameter("orderId", orderId)
            .getResultList();
        if (items.isEmpty()) {
            throw new EntityNotFoundException("cannot find order items for id: " + orderId);
        }
        return items;
    }
}
