package com.mycompany.order.infra.port.outbound.http.currency;

import com.mycompany.order.domain.Currency;
import com.mycompany.order.domain.Price;

import java.math.BigDecimal;
import java.util.Map;

import static com.mycompany.order.domain.Currency.*;

final class ExchangeCurrencyPortAdapter implements ExchangeCurrencyPort {

    private final Map<Currency, Map<Currency, Double>> exchangeRates = Map.of(
        USD, Map.of(
            PLN, 4.4145,
            EUR, 1.0342
        )
    );

    @Override
    public Price exchange(Price price, Currency currency) {
        return price.exchangeTo(currency, getExchangeRate(price, currency));
    }

    private BigDecimal getExchangeRate(Price price, Currency currency) {
        return price.currency().equals(currency)
            ? BigDecimal.ONE
            : BigDecimal.valueOf(exchangeRates.get(currency).get(price.currency()));
    }
}
