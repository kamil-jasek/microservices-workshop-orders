package com.mycompany.order.infra.port.inbound.http.write;

import com.mycompany.order.command.MakeOrderCmd;
import com.mycompany.order.domain.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

import static java.util.stream.Collectors.toList;

record MakeOrderRequest(
    @NotNull @UUID
    String customerId,
    @NotNull @Pattern(regexp = "USD|PLN|EUR")
    String orderCurrency,
    @NotEmpty @NotNull @Valid
    List<OrderItemDto> orderItems,
    String discountCoupon
) {
    public MakeOrderCmd toCommand() {
        return new MakeOrderCmd(
            CustomerId.of(customerId),
            mapOrderItems(orderItems),
            Currency.valueOf(orderCurrency),
            DiscountCoupon.of(discountCoupon)
        );
    }

    private List<OrderItem> mapOrderItems(List<OrderItemDto> orderItems) {
        return orderItems.stream()
            .map(item -> new OrderItem(
                ProductId.of(item.productId()),
                Price.of(item.price().value(), Currency.valueOf(item.price().currency())),
                Weight.of(item.weight().value(), WeightUnit.valueOf(item.weight().unit())),
                Quantity.of(item.quantity())
            ))
            .collect(toList());
    }
}
