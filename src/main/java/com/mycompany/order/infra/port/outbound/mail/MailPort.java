package com.mycompany.order.infra.port.outbound.mail;

import java.util.List;

public interface MailPort {
    void send(String subject, String body, List<String> recipients);
}
