package com.mycompany.order.infra.port.inbound.kafka.warehouse;

import com.mycompany.application.command.CommandDispatcher;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@Profile("!processmanager")
class KafkaWarehouseConfig {

    @Bean
    KafkaWarehouseListener kafkaWarehouseListener(@NonNull CommandDispatcher commandDispatcher) {
        return new KafkaWarehouseListener(commandDispatcher);
    }
}
