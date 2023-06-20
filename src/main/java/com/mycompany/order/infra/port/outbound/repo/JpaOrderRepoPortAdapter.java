package com.mycompany.order.infra.port.outbound.repo;

import com.mycompany.application.exception.DomainIllegalArgumentException;
import com.mycompany.order.domain.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static java.lang.String.format;

@RequiredArgsConstructor
final class JpaOrderRepoPortAdapter implements OrderRepoPort {

    private final @NonNull OrderEntityRepo repo;
    private final @NonNull OrderEntityMapper mapper;

    @Override
    public Order getOrder(OrderId orderId) {
        return mapper.toDomain(repo.getReferenceById(orderId.id()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Order> T getOrderAs(OrderId orderId, Class<T> clazz) {
        final var order = getOrder(orderId);
        if (order.getClass().isAssignableFrom(clazz)) {
            return (T) order;
        } else {
            throw new DomainIllegalArgumentException(
                format("order with id: %s has unsupported status: %s for this operation",
                    orderId.id(),
                    order.status()));
        }
    }

    @Override
    public void save(Order order) {
        if (order instanceof AcceptedOrder) {
            repo.save(mapper.toEntity((AcceptedOrder) order));
        } else if (order instanceof CanceledOrder) {
            repo.save(mapper.toEntity((CanceledOrder) order));
        } else if (order instanceof SentOrder) {
            repo.save(mapper.toEntity((SentOrder) order));
        } else {
            throw new DomainIllegalArgumentException(
                format("order with id: %s has unknown status: %s",
                    order.id().id(),
                    order.status()));
        }
    }
}
