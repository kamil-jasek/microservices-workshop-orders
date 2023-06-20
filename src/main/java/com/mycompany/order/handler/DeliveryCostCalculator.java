package com.mycompany.order.handler;

import com.mycompany.order.domain.Price;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.mycompany.order.infra.port.outbound.http.currency.ExchangeCurrencyPort;
import com.mycompany.order.domain.ExchangedOrderItemList;

@RequiredArgsConstructor
final class DeliveryCostCalculator {

    private final @NonNull DeliveryCostSettings settings;
    private final @NonNull ExchangeCurrencyPort currencyPort;

    Price calculate(@NonNull ExchangedOrderItemList orderItems) {
        final var totalPrice = orderItems.totalPrice();
        final var totalWeight = orderItems.totalWeight();
        return settings
            .deliveryCostLevels()
            .stream()
            .filter(level -> level.matches(totalPrice, totalWeight))
            .findFirst()
            .map(DeliveryCostSettings.DeliveryCostLevel::deliveryCost)
            .orElseGet(() -> currencyPort
                .exchange(settings.defaultDeliveryCost(), orderItems.baseCurrency()));
    }
}
