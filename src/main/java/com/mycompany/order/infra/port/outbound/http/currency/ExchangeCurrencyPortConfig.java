package com.mycompany.order.infra.port.outbound.http.currency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ExchangeCurrencyPortConfig {

    @Bean
    ExchangeCurrencyPort exchangeCurrencyPort() {
        return new ExchangeCurrencyPortAdapter();
    }
}
