package com.mycompany.application.rest.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.NonNull;

import java.util.Collection;

public record HateoasCollection<T>(
    @NonNull Collection<T> results,
    @NonNull @JsonUnwrapped HateoasLinks links
) {

    @JsonProperty
    int size() {
        return results.size();
    }
}

