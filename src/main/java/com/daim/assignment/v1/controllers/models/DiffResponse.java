package com.daim.assignment.v1.controllers.models;

import com.daim.assignment.domain.Diff;

public class DiffResponse {

    private String diff;

    public DiffResponse() {

    }

    public DiffResponse(Diff diff) {
        this.diff = diff.compareLeftAndRight();
    }

    public String getDiff() {
        return this.diff;
    }
}
