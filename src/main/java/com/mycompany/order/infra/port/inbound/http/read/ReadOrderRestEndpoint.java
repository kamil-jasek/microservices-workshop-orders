package com.mycompany.order.infra.port.inbound.http.read;

import com.mycompany.application.rest.hateoas.HateoasCollection;
import com.mycompany.application.rest.hateoas.HateoasLink;
import com.mycompany.application.rest.hateoas.HateoasLinks;
import com.mycompany.application.rest.hateoas.HateoasPage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:[*]")
final class ReadOrderRestEndpoint {

    private final OrderView orderView;

    @GetMapping
    HateoasPage<OrderDto> listOrders(@Valid OrderFilter filter) {
        return new HateoasPage<>(orderView.filter(filter), new HateoasLinks(Map.of(
            "self", HateoasLink.of("/v1/orders"),
            "first", HateoasLink.of("/v1/orders?page=1")
        )));
    }

    @GetMapping("/{id}")
    OrderDto getById(@PathVariable UUID id) {
        return orderView.getById(id);
    }

    @GetMapping("/{id}/items")
    HateoasCollection<OrderItemDto> listOrderItems(@PathVariable UUID id) {
        return new HateoasCollection<>(orderView.getOrderItems(id), new HateoasLinks(Map.of(
            "self", HateoasLink.of("/v1/orders/" + id + "/items"),
            "order", HateoasLink.of("/v1/orders/" + id)
        )));
    }
}
