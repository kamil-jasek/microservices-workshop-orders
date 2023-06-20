package com.mycompany.order.handler;

import com.mycompany.application.command.CommandHandler;
import com.mycompany.application.event.DomainEventPublisher;
import com.mycompany.order.command.MakeOrderCmd;
import com.mycompany.order.domain.AcceptedOrder;
import com.mycompany.order.domain.OrderId;
import com.mycompany.order.event.OrderDataV1;
import com.mycompany.order.event.OrderMadeV1;
import com.mycompany.order.exception.CustomerNotExistsException;
import com.mycompany.order.handler.DiscountCalculator.DiscountContext;
import com.mycompany.order.infra.port.outbound.http.customer.CustomerPort;
import com.mycompany.order.infra.port.outbound.repo.OrderRepoPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

import static com.mycompany.order.command.MakeOrderCmd.MakeOrderConfirmation;
import static java.util.UUID.randomUUID;

@Transactional
@RequiredArgsConstructor
class MakeOrderHandler extends CommandHandler<MakeOrderCmd, MakeOrderConfirmation> {

    private final @NonNull CustomerPort customerPort;
    private final @NonNull OrderRepoPort orderRepoPort;
    private final @NonNull Clock clock;
    private final @NonNull OrderItemCurrencyExchanger orderItemCurrencyExchanger;
    private final @NonNull DeliveryCostCalculator deliveryCostCalculator;
    private final @NonNull DiscountCalculator discountCalculator;
    private final @NonNull DomainEventPublisher eventPublisher;

    public MakeOrderConfirmation handle(MakeOrderCmd cmd) {
        if (!customerPort.customerExists(cmd.customerId())) {
            throw new CustomerNotExistsException(cmd.customerId());
        }
        final var exchangedOrderItems = orderItemCurrencyExchanger.exchangeCurrencies(
            cmd.orderItemList(),
            cmd.orderCurrency());
        final var deliveryCost = deliveryCostCalculator.calculate(exchangedOrderItems);
        final var discount = discountCalculator.calculate(new DiscountContext(
                cmd.customerId(),
                exchangedOrderItems,
                deliveryCost,
                cmd.discountCoupon()))
            .discount();
        final var order = new AcceptedOrder(
            OrderId.generate(),
            clock.instant(),
            cmd.customerId(),
            exchangedOrderItems,
            deliveryCost,
            discount);
        orderRepoPort.save(order);
        eventPublisher.publish(new OrderMadeV1(
            randomUUID(),
            clock.instant(),
            null,
            OrderDataV1.from(order)));
        return new MakeOrderConfirmation(order.id());
    }
}
