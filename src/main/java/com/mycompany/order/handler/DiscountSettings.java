package com.mycompany.order.handler;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;
import java.util.List;

@ConfigurationProperties("orders.discount-settings")
record DiscountSettings(List<LocalDate> freeDeliveryDays) {
}
