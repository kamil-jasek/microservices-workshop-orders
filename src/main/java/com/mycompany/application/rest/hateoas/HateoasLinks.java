package com.mycompany.application.rest.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

import static java.util.Collections.emptyMap;

public record HateoasLinks(
    @JsonProperty("_links")
    Map<String, HateoasLink> links
) {

    public static HateoasLinks none() {
        return new HateoasLinks(emptyMap());
    }
}
