package com.mycompany.order.infra.port.inbound.kafka.warehouse;

import com.mycompany.application.command.CommandDispatcher;
import com.mycompany.order.command.CancelOrderCmd;
import com.mycompany.order.command.SendOrderCmd;
import com.mycompany.order.domain.OrderId;
import com.mycompany.order.domain.ShipmentId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
final class KafkaWarehouseListener {

    private final @NonNull CommandDispatcher commandDispatcher;

    @SneakyThrows
    @KafkaListener(
        id = "orders-stock-released-listener",
        topics = "warehouse-stock-released-v1",
        containerFactory = "domainEventKafkaContainerFactory"
    )
    void handleStockReleased(StockReleasedV1 event) {
        commandDispatcher.dispatch(new SendOrderCmd(
            new OrderId(event.data().waybillId()),
            new ShipmentId(event.data().shipmentId())));
    }

    @SneakyThrows
    @KafkaListener(
        id = "orders-product-out-of-stock-listener",
        topics = "warehouse-product-out-of-stock-v1",
        containerFactory = "domainEventKafkaContainerFactory"
    )
    void handleProductOutOfStock(ProductOutOfStockV1 event) {
        commandDispatcher.dispatch(new CancelOrderCmd(
            new OrderId(event.data().waybillId()),
            "product out of stock: " + event.data().productId()));
    }
}
