package com.mycompany.order.domain;

public record OrderItem(ProductId productId,
                        Price price,
                        Weight weight,
                        Quantity quantity) {
}
