package com.mycompany.order.command;

import com.mycompany.application.command.Command;
import com.mycompany.order.domain.OrderId;
import com.mycompany.order.domain.ShipmentId;

import java.time.Instant;

import static com.mycompany.order.command.SendOrderCmd.OrderSentConfirmation;

public record SendOrderCmd(OrderId orderId, ShipmentId shipmentId) implements Command<OrderSentConfirmation> {

    public record OrderSentConfirmation(OrderId orderId, Instant sentAt) {
    }
}
