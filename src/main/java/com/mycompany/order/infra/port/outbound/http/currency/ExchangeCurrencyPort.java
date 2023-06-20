package com.mycompany.order.infra.port.outbound.http.currency;

import com.mycompany.order.domain.Currency;
import com.mycompany.order.domain.Price;

public interface ExchangeCurrencyPort {
    Price exchange(Price price, Currency currency);
}
