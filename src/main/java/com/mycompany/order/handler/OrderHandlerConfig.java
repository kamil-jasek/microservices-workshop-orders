package com.mycompany.order.handler;

import com.mycompany.application.event.DomainEventPublisher;
import com.mycompany.order.infra.port.outbound.http.customer.CustomerPort;
import com.mycompany.order.infra.port.outbound.http.discount.DiscountPort;
import com.mycompany.order.infra.port.outbound.http.currency.ExchangeCurrencyPort;
import com.mycompany.order.infra.port.outbound.repo.OrderRepoPort;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.List;

@Configuration
@EnableConfigurationProperties({
    DeliveryCostSettings.class,
    DiscountSettings.class
})
class OrderHandlerConfig {

    @Bean
    MakeOrderHandler makeOrderHandler(CustomerPort customerPort,
                                      OrderRepoPort repoPort,
                                      Clock clock,
                                      ExchangeCurrencyPort currencyPort,
                                      DeliveryCostSettings deliveryCostSettings,
                                      DiscountPort discountPort,
                                      DiscountSettings discountSettings,
                                      DomainEventPublisher eventPublisher) {
        return new MakeOrderHandler(customerPort,
            repoPort,
            clock,
            new OrderItemCurrencyExchanger(currencyPort),
            new DeliveryCostCalculator(deliveryCostSettings, currencyPort),
            discountCalculator(discountPort, discountSettings, clock),
            eventPublisher);
    }

    @Bean
    SendOrderHandler sentOrderHandler(OrderRepoPort repoPort,
                                      DomainEventPublisher eventPublisher,
                                      Clock clock) {
        return new SendOrderHandler(repoPort, eventPublisher, clock);
    }

    @Bean
    CancelOrderHandler cancelOrderHandler(OrderRepoPort repoPort,
                                          DomainEventPublisher eventPublisher,
                                          Clock clock) {
        return new CancelOrderHandler(repoPort, eventPublisher, clock);
    }

    private DiscountCalculator discountCalculator(DiscountPort discountPort,
                                                  DiscountSettings settings,
                                                  Clock clock) {
        return new CompositeDiscountCalculator(List.of(
            new OneTimeCouponDiscountCalculator(discountPort),
            new FreeDeliveryDiscountCalculator(settings.freeDeliveryDays(), clock)));
    }
}
