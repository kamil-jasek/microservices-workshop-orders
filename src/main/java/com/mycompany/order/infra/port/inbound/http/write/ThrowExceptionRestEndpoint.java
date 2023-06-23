package com.mycompany.order.infra.port.inbound.http.write;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/error")
final class ThrowExceptionRestEndpoint {

    @PostMapping
    void throwError() {
        throw new UnsupportedOperationException("not implemented");
    }
}
