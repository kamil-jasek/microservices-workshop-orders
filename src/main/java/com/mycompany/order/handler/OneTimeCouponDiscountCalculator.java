package com.mycompany.order.handler;

import com.mycompany.order.domain.Price;
import com.mycompany.order.infra.port.outbound.http.discount.DiscountPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static java.math.BigDecimal.ZERO;

@RequiredArgsConstructor
final class OneTimeCouponDiscountCalculator implements DiscountCalculator {

    private final @NonNull DiscountPort couponPort;

    @Override
    public DiscountContext calculate(DiscountContext discountContext) {
        if (discountContext.discount()
            .isHigherThan(new Price(ZERO, discountContext.orderItems().baseCurrency()))) {
            return discountContext;
        }
        return discountContext.discountCoupon()
            .flatMap(coupon -> couponPort.deactivateCoupon(discountContext.customerId(), coupon))
            .map(discount -> discountContext.applyDiscount(discountContext.orderItems()
                .totalPrice()
                .multiply(discount.percentage().toDecimal())))
            .orElse(discountContext);
    }
}
