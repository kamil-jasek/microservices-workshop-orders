package com.mycompany.application.rest.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.util.Assert;

import java.util.Collection;

import static java.util.Collections.emptyMap;

@Getter(onMethod = @__(@JsonProperty))
public final class HateoasPage<T> {

    private final Collection<T> results;
    private final int page;
    private final int pageSize;
    private final int totalElements;
    @JsonUnwrapped
    private final HateoasLinks links;

    public HateoasPage(Collection<T> results) {
        this(results, 1, results.size(), results.size(), new HateoasLinks(emptyMap()));
    }

    public HateoasPage(Collection<T> results, HateoasLinks links) {
        this(results, 1, results.size(), results.size(), links);
    }

    public HateoasPage(@NonNull Collection<T> results,
                       int page,
                       int pageSize,
                       int totalElements,
                       @NonNull HateoasLinks links) {
        Assert.isTrue(page > 0, "page number must be gt 0");
        Assert.isTrue(pageSize > -1 && pageSize <= results.size(),
            "page size must be positive and cannot be gt results size");
        Assert.isTrue(totalElements >= pageSize && totalElements >= results.size(),
            "total elements must be gte page size and results size");
        this.results = results;
        this.page = page;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.links = links;
    }
}
