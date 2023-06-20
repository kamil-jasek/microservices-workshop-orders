package com.mycompany.order.infra.port.inbound.http.read;

import java.util.List;

record OrderListDto(
    List<OrderDto> results
) {
}
