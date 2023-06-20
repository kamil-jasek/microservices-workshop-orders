package com.mycompany.order.infra.port.inbound.http.read;

import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan
class OrderViewConfig {

    @Bean
    OrderView orderView(EntityManager em) {
        return new OrderView(em);
    }
}
