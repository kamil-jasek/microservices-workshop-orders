package com.mycompany.order.infra.port.outbound.http.customer;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = CustomerRestApi.class)
class CustomerPortConfig {

    @Bean
    CustomerPort customerPort(CustomerRestApi api) {
        return new CustomerPortAdapter(api);
    }

    @Bean
    FallbackCustomerRestApi fallbackCustomerRestApi() {
        return new FallbackCustomerRestApi();
    }
}
