package com.mycompany.application.util;

import com.mycompany.application.exception.DomainIllegalArgumentException;

public final class DomainArgumentCheck {

    public static void check(boolean condition, String message) {
        if (!condition) {
            throw new DomainIllegalArgumentException(message);
        }
    }
}
