package com.mycompany.order.infra.port.inbound.kafka.mailnotification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.order.infra.port.outbound.mail.MailPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
@EnableKafka
@Profile("!processmanager")
class MailNotificationConfig {

    private final ObjectMapper mapper = new ObjectMapper()
        .disable(FAIL_ON_UNKNOWN_PROPERTIES)
        .disable(WRITE_DATES_AS_TIMESTAMPS)
        .findAndRegisterModules();

    @Bean
    MailNotificationListener mailNotificationListener(MailPort mailPort) {
        return new MailNotificationListener(mailPort, mapper);
    }

}
