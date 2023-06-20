package com.mycompany.order.exception;

import com.mycompany.order.domain.CustomerId;
import com.mycompany.application.exception.DomainIllegalArgumentException;

public final class CustomerNotExistsException extends DomainIllegalArgumentException {

    public CustomerNotExistsException(CustomerId customerId) {
        super("customer not exists with id: " + customerId.id());
    }
}
