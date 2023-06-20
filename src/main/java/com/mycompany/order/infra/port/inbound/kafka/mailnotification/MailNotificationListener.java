package com.mycompany.order.infra.port.inbound.kafka.mailnotification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.order.event.OrderCanceledV1;
import com.mycompany.order.event.OrderMadeV1;
import com.mycompany.order.event.OrderSentV1;
import com.mycompany.order.infra.port.outbound.mail.MailPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
final class MailNotificationListener {

    private final MailPort mailPort;
    private final ObjectMapper mapper;

    @SneakyThrows
    @KafkaListener(
        id = "orders-order-made-listener",
        topics = "orders-order-made-v1",
        containerFactory = "domainEventKafkaContainerFactory"
    )
    public void handleOrderMade(@NonNull OrderMadeV1 event) {
        log.info("sending email order made id: {}", event.data().id());
        // prepare and send email
        mailPort.send("subject", "body", List.of("recipient@test.pl"));
    }

    @SneakyThrows
    @KafkaListener(
        id = "orders-order-sent-listener",
        topics = "orders-order-sent-v1",
        containerFactory = "domainEventKafkaContainerFactory"
    )
    public void handleOrderSent(@NonNull OrderSentV1 event) {
        log.info("sending email order sent id: {}", event.data().id());
        // prepare and send email
        mailPort.send("subject", "body", List.of("recipient@test.pl"));
    }

    @SneakyThrows
    @KafkaListener(
        id = "orders-order-canceled-listener",
        topics = "orders-order-canceled-v1",
        containerFactory = "domainEventKafkaContainerFactory"
    )
    public void handleOrderCanceled(@NonNull OrderCanceledV1 event) {
        log.info("sending email order canceled id: {}", event.data().id());
        // prepare and send email
        mailPort.send("subject", "body", List.of("recipient@test.pl"));
    }
}
