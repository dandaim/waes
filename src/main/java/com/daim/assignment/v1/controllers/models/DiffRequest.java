package com.daim.assignment.v1.controllers.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiffRequest {

    private String data;

    public DiffRequest(@JsonProperty("data") String data) {
        this.data = data;
    }

    public String getData() {
        return this.data;
    }
}
