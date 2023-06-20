package com.mycompany.order.infra.port.inbound.http.write;

import com.mycompany.application.command.CommandDispatcher;
import com.mycompany.order.command.MakeOrderCmd.MakeOrderConfirmation;
import com.mycompany.order.domain.OrderId;
import com.mycompany.order.infra.port.inbound.http.write.WriteOrderRestEndpoint;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {WriteOrderRestEndpoint.class})
class WriteOrderRestEndpointTest {

    @MockBean
    private CommandDispatcher dispatcher;

    @Autowired
    private MockMvc mvc;

    @Test
    @SneakyThrows
    void should_send_request_to_make_order() {
        // given handled request to make an order
        given(dispatcher.dispatch(any())).willReturn(
            new MakeOrderConfirmation(OrderId.of("a3d6c489-3f1d-435e-a5cd-27c679b46d7d")));

        // when post request is performed
        mvc.perform(post("/v1/orders")
                .contentType(APPLICATION_JSON)
                .content("""
                    {
                        "customerId": "12992c00-39ab-4e7e-9710-b73db119a09e",
                        "orderCurrency": "USD",
                        "orderItems": [
                            {
                                "productId": "87b899d8-3c87-4889-80e2-34b7d4ac0f53",
                                "price": {
                                    "value": "24.12",
                                    "currency": "EUR"
                                },
                                "weight": {
                                    "value": 0.12,
                                    "unit": "KG"
                                },
                                "quantity": 2
                            }
                        ],
                        "discountCoupon": "ABC20"
                    }
                    """))
            // then expect response 201 and location header
            .andExpect(status().isCreated())
            .andExpect(header().string("location", "/v1/orders/a3d6c489-3f1d-435e-a5cd-27c679b46d7d"));
    }
}