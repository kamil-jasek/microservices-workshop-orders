package com.mycompany.order.handler;

import com.mycompany.application.command.CommandHandler;
import com.mycompany.application.event.DomainEventPublisher;
import com.mycompany.order.command.SendOrderCmd;
import com.mycompany.order.command.SendOrderCmd.OrderSentConfirmation;
import com.mycompany.order.domain.AcceptedOrder;
import com.mycompany.order.event.OrderDataV1;
import com.mycompany.order.event.OrderSentV1;
import com.mycompany.order.infra.port.outbound.repo.OrderRepoPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

import static java.util.UUID.randomUUID;

@Transactional
@RequiredArgsConstructor
class SendOrderHandler extends CommandHandler<SendOrderCmd, OrderSentConfirmation> {

    private final @NonNull OrderRepoPort repoPort;
    private final @NonNull DomainEventPublisher eventPublisher;
    private final @NonNull Clock clock;

    @Override
    public OrderSentConfirmation handle(SendOrderCmd cmd) {
        final var acceptedOrder = repoPort.getOrderAs(cmd.orderId(), AcceptedOrder.class);
        final var sentOrder = acceptedOrder.sentAt(clock.instant());
        repoPort.save(sentOrder);
        eventPublisher.publish(new OrderSentV1(
            randomUUID(),
            clock.instant(),
            null,
            OrderDataV1.from(sentOrder)
        ));
        return new OrderSentConfirmation(sentOrder.id(), sentOrder.sentTime());
    }
}
