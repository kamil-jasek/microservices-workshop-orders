package com.mycompany.order.handler;

import com.mycompany.order.command.MakeOrderCmd;
import com.mycompany.order.domain.*;
import com.mycompany.order.event.OrderMadeV1;
import com.mycompany.order.exception.CustomerNotExistsException;
import com.mycompany.order.infra.port.outbound.repo.OrderRepoPort;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

import static com.mycompany.order.domain.Currency.*;
import static com.mycompany.order.domain.OrderStatus.ACCEPTED;
import static com.mycompany.order.domain.WeightUnit.KG;
import static com.mycompany.order.domain.WeightUnit.LB;
import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class MakeOrderHandlerTest {

    private final OrderRepoPort orderRepoPort = new TestOrderRepoPort();
    private final TestExchangeCurrencyPort currencyPort = new TestExchangeCurrencyPort();
    private final TestDomainEventPublisher eventPublisher = new TestDomainEventPublisher();

    private final MakeOrderHandler handler = new MakeOrderHandler(
        new TestCustomerPort(),
        orderRepoPort,
        Clock.fixed(Instant.parse("2022-02-20T10:00:01Z"), UTC),
        new OrderItemCurrencyExchanger(currencyPort),
        new DeliveryCostCalculator(
            new DeliveryCostSettings(Price.of("20.00", USD), List.of()),
            currencyPort),
        new CompositeDiscountCalculator(List.of(
            new OneTimeCouponDiscountCalculator(new TestDiscountPort())
        )),
        eventPublisher
    );

    @Test
    void should_make_order_with_discount() {
        // given customer id
        final var customerId = CustomerId.of("9c74dd58-83e3-4a21-8220-bec2ddcd590a");
        // given order items with different currencies
        final var orderItems = List.of(
            new OrderItem(
                ProductId.of("abeffc80-71b8-43d0-9b57-bdda6f963625"),
                Price.of("24.50", EUR),
                Weight.of(0.20, LB),
                Quantity.of(1)),
            new OrderItem(
                ProductId.of("6e6a0cba-37a6-4c88-a098-710e2823c549"),
                Price.of("32.50", PLN),
                Weight.of(0.12, KG),
                Quantity.of(3)
            ));
        // given order currency USD
        final var orderCurrency = USD;
        // given coupon with 20% discount
        final var discountCoupon = DiscountCoupon.of("ABC20");

        // when make order is handled
        final var orderConfirmation = handler.handle(new MakeOrderCmd(
            customerId,
            orderItems,
            orderCurrency,
            discountCoupon));

        // then order is confirmed
        assertThat(orderConfirmation.orderId()).isNotNull();
        final var order = orderRepoPort.getOrder(orderConfirmation.orderId());
        assertThat(order.status()).isEqualTo(ACCEPTED);
        // and order has correct data
        assertThat(order).isEqualTo(new AcceptedOrder(
                orderConfirmation.orderId(),
                Clock
                    .fixed(Instant.parse("2022-02-20T10:00:01Z"), UTC)
                    .instant(),
                customerId,
                // order item prices are exchanged to USD (order currency)
                new ExchangedOrderItemList(
                    orderCurrency,
                    new ExchangedOrderItem(
                        new OrderItem(
                            ProductId.of("abeffc80-71b8-43d0-9b57-bdda6f963625"),
                            Price.of("24.50", EUR),
                            Weight.of(0.20, LB),
                            Quantity.of(1)),
                        Price.of("25.34", USD)
                    ),
                    new ExchangedOrderItem(
                        new OrderItem(
                            ProductId.of("6e6a0cba-37a6-4c88-a098-710e2823c549"),
                            Price.of("32.50", PLN),
                            Weight.of(0.12, KG),
                            Quantity.of(3)
                        ),
                        Price.of("143.47", USD)
                    )),
                // and default delivery cost is applied -> 20 USD
                Price.of("20.00", USD),
                // and discountCoupon is 20% of order items total price 455.75 USD
                Price.of("91.15", USD)
            )
        );
        // and order items total price (455.75USD) + delivery cost (20.00 USD) is 475.75 USD
        assertThat(order.totalPrice()).isEqualTo(Price.of("475.75", USD));
        // and order items discounted total price by 20% is 364.44 USD + delivery cost 20 USD is 384.44 USD
        assertThat(order.discountedTotalPrice()).isEqualTo(Price.of("384.60", USD));
        // and event about created order is published
        assertThat(eventPublisher.hasPublished(OrderMadeV1.class)).isTrue();
    }

    @Test
    void should_not_make_an_order_if_customer_not_exists() {
        // given customer id and order items
        final var customerId = CustomerId.of("c776b2aa-14fa-465b-aa85-be2bc19320b6");
        final var orderItems = List.of(
            new OrderItem(
                ProductId.of("abeffc80-71b8-43d0-9b57-bdda6f963625"),
                Price.of("24.50", USD),
                Weight.of(0.20, KG),
                Quantity.of(1)));

        // expect exception when make order is handled
        assertThrows(CustomerNotExistsException.class, () -> handler.handle(new MakeOrderCmd(
            customerId, orderItems, USD, DiscountCoupon.none())));
    }
}
