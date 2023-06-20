package com.mycompany.application.rest.hateoas;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public interface HateoasResource {

    @JsonUnwrapped
    default HateoasLinks links() {
        return HateoasLinks.none();
    }
}
