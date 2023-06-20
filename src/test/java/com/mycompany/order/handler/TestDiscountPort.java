package com.mycompany.order.handler;

import com.mycompany.order.infra.port.outbound.http.discount.DiscountPort;
import com.mycompany.order.infra.port.outbound.http.discount.DiscountPort.Discount.DiscountPercentage;
import com.mycompany.order.domain.CustomerId;
import com.mycompany.order.domain.DiscountCoupon;

import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;

final class TestDiscountPort implements DiscountPort {

    private final Map<CustomerId, Map<DiscountCoupon, Discount>> discounts = Map.of(
        CustomerId.of("9c74dd58-83e3-4a21-8220-bec2ddcd590a"), Map.of(
            new DiscountCoupon("ABC20"), new Discount("ABC20", new DiscountPercentage(20))
        )
    );

    @Override
    public Optional<Discount> deactivateCoupon(CustomerId customerId, DiscountCoupon coupon) {
        return Optional.ofNullable(discounts.getOrDefault(customerId, emptyMap()).get(coupon));
    }
}
