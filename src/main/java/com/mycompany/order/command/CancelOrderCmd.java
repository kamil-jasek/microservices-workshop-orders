package com.mycompany.order.command;

import com.mycompany.application.command.Command;
import com.mycompany.order.domain.OrderId;
import lombok.NonNull;

import java.time.Instant;

import static com.mycompany.order.command.CancelOrderCmd.OrderCancelConfirmation;

public record CancelOrderCmd(@NonNull OrderId orderId,
                             @NonNull String reason) implements Command<OrderCancelConfirmation> {

    public record OrderCancelConfirmation(OrderId orderId, Instant cancelTime) {
    }
}
