package com.mycompany.order.infra.port.outbound.http.customer;

import com.mycompany.order.domain.CustomerId;
import com.mycompany.order.infra.port.outbound.http.customer.CustomerPort;
import com.mycompany.order.infra.port.outbound.http.customer.CustomerPortConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import static org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties.StubsMode.CLASSPATH;

@SpringBootTest(webEnvironment = NONE, classes = {
    CustomerPortConfig.class,
    FeignAutoConfiguration.class,
    HttpMessageConvertersAutoConfiguration.class
})
@AutoConfigureStubRunner(
    stubsMode = CLASSPATH,
    ids = {
        "com.mycompany:customers:+:stubs:6565"
    }
)
@TestPropertySource(properties = {
    "spring.cloud.openfeign.client.config.customers.url=http://localhost:6565"
})
final class CustomerPortAdapterTest {

    @Autowired
    private CustomerPort port;

    @Test
    void should_get_true_if_customer_exists() {
        //when
        final var result = port.customerExists(CustomerId.of("bdad7e29-b001-4058-b631-7a77e8119729"));

        // then
        assertTrue(result);
    }

    @Test
    void should_get_false_if_customer_not_exists() {
        // when
        final var result = port.customerExists(CustomerId.of("573aae80-bbec-4264-b62c-f09859baa1d4"));

        // then
        assertFalse(result);
    }
}
