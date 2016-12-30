package com.rivancic.aluna.repositories.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by rivancic on 30/12/2016.
 */
public class Sources {

    private String uri;
    private String name;

    @JsonCreator
    public Sources(@JsonProperty("name")String name, @JsonProperty("uri") String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getUri() {

        return uri;
    }

    public String getName() {

        return name;
    }
}
