package com.mycompany.order.command;

import com.mycompany.application.command.Command;
import com.mycompany.order.domain.*;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

import static com.mycompany.order.command.MakeOrderCmd.MakeOrderConfirmation;

public record MakeOrderCmd(
    @NonNull CustomerId customerId,
    @NonNull List<OrderItem> orderItemList,
    @NonNull Currency orderCurrency,
    @NonNull Optional<DiscountCoupon> discountCoupon
) implements Command<MakeOrderConfirmation> {

    public record MakeOrderConfirmation(OrderId orderId) {
    }
}
