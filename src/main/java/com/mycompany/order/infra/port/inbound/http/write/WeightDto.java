package com.mycompany.order.infra.port.inbound.http.write;

import jakarta.validation.constraints.Pattern;

record WeightDto(double value, @Pattern(regexp = "KG|GM|LB") String unit) {
}
