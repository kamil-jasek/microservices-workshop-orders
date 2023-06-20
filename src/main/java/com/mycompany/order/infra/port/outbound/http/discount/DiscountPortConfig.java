package com.mycompany.order.infra.port.outbound.http.discount;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DiscountPortConfig {

    @Bean
    DiscountPort discountPort() {
        return new DummyDiscountPortAdapter();
    }
}
