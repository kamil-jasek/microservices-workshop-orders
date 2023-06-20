package com.mycompany.order.handler;

import com.mycompany.order.domain.Currency;
import com.mycompany.order.domain.ExchangedOrderItem;
import com.mycompany.order.domain.OrderItem;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.mycompany.order.domain.ExchangedOrderItemList;
import com.mycompany.order.infra.port.outbound.http.currency.ExchangeCurrencyPort;

import java.util.List;

@RequiredArgsConstructor
final class OrderItemCurrencyExchanger {

    private final @NonNull ExchangeCurrencyPort currencyPort;

    ExchangedOrderItemList exchangeCurrencies(List<OrderItem> orderItems,
                                              Currency orderCurrency) {
        return new ExchangedOrderItemList(orderCurrency, orderItems
            .stream()
            .map(orderItem -> exchangeOrderItem(orderCurrency, orderItem))
            .toArray(ExchangedOrderItem[]::new));
    }

    private ExchangedOrderItem exchangeOrderItem(Currency currency, OrderItem orderItem) {
        return new ExchangedOrderItem(orderItem, currencyPort.exchange(orderItem.price(), currency));
    }
}
