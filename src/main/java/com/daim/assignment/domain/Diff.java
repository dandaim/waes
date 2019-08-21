package com.daim.assignment.domain;

import com.daim.assignment.repositories.models.DiffEntity;

public class Diff {

    private Long id;
    private String leftData;
    private String rightData;

    public Diff(Long id, String leftData, String rightData) {
        this.id = id;
        this.leftData = leftData;
        this.rightData = rightData;
    }

    public Long getId() {
        return this.id;
    }

    public DiffEntity toDiffEntity() {
        return new DiffEntity(this.id, this.leftData, this.rightData);
    }

    public String getLeftData() {
        return this.leftData;
    }

    public String getRightData() {
        return this.rightData;
    }
}
