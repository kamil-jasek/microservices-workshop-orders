package com.mycompany.application.rest.hateoas;

import java.net.URI;

public record HateoasLink(URI href) {

    public static HateoasLink of(String href) {
        return new HateoasLink(URI.create(href));
    }
}
