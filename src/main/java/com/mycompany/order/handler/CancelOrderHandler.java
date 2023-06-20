package com.mycompany.order.handler;

import com.mycompany.application.command.CommandHandler;
import com.mycompany.application.event.DomainEventPublisher;
import com.mycompany.order.command.CancelOrderCmd;
import com.mycompany.order.domain.AcceptedOrder;
import com.mycompany.order.event.OrderCanceledV1;
import com.mycompany.order.event.OrderDataV1;
import com.mycompany.order.infra.port.outbound.repo.OrderRepoPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

import static com.mycompany.order.command.CancelOrderCmd.OrderCancelConfirmation;
import static java.util.UUID.randomUUID;

@Transactional
@RequiredArgsConstructor
class CancelOrderHandler extends CommandHandler<CancelOrderCmd, OrderCancelConfirmation> {

    private final @NonNull OrderRepoPort repoPort;
    private final @NonNull DomainEventPublisher eventPublisher;
    private final @NonNull Clock clock;

    @Override
    public OrderCancelConfirmation handle(CancelOrderCmd cmd) {
        final var acceptedOrder = repoPort.getOrderAs(cmd.orderId(), AcceptedOrder.class);
        final var canceledOrder = acceptedOrder.cancel(clock.instant(), cmd.reason());
        repoPort.save(canceledOrder);
        eventPublisher.publish(new OrderCanceledV1(
            randomUUID(),
            clock.instant(),
            null,
            OrderDataV1.from(canceledOrder)
        ));
        return new OrderCancelConfirmation(canceledOrder.id(), canceledOrder.cancelTime());
    }
}
