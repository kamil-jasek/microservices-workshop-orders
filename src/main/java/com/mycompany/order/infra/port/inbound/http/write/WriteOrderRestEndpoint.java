package com.mycompany.order.infra.port.inbound.http.write;

import com.mycompany.application.command.CommandDispatcher;
import com.mycompany.order.command.CancelOrderCmd;
import com.mycompany.order.command.SendOrderCmd;
import com.mycompany.order.domain.OrderId;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:[*]")
final class WriteOrderRestEndpoint {

    final @NonNull CommandDispatcher dispatcher;

    @PostMapping
    @SneakyThrows
    ResponseEntity<Void> makeOrder(@RequestBody @Valid MakeOrderRequest request) {
        final var orderConfirmation = dispatcher.dispatch(request.toCommand());
        return ResponseEntity
            .created(new URI("/v1/orders/" + orderConfirmation.orderId().id()))
            .build();
    }

    @PutMapping("/{id}/send")
    OrderSentDto sendOrder(@PathVariable UUID id) {
        final var confirmation = dispatcher.dispatch(new SendOrderCmd(new OrderId(id)));
        return new OrderSentDto(confirmation.orderId().id(), confirmation.sentAt());
    }

    @PutMapping("/{id}/cancel")
    OrderCanceledDto cancelOrder(@PathVariable UUID id,
                                 @RequestBody @Valid CancelOrderRequest request) {
        final var confirmation = dispatcher.dispatch(
            new CancelOrderCmd(new OrderId(id), request.reason()));
        return new OrderCanceledDto(confirmation.orderId().id(), confirmation.cancelTime());
    }
}
