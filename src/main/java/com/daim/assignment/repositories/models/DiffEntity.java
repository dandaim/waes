package com.daim.assignment.repositories.models;

import com.daim.assignment.domain.Diff;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class DiffEntity {

    @Id
    private Long id;

    private String left;

    private String right;

    public DiffEntity() {

    }

    public DiffEntity(Long id, String left, String right) {
        this.id = id;
        this.left = left;
        this.right = right;
    }

    public DiffEntity(DiffEntity diffEntity, Diff diff) {
        this.id = diffEntity.id;
        this.left = Objects.isNull(diff.getLeftData()) ? diffEntity.left : diff.getLeftData();
        this.right = Objects.isNull(diff.getRightData()) ? diffEntity.right : diff.getRightData();
    }

    public Long getId() {
        return this.id;
    }

    public String getLeft() {
        return this.left;
    }

    public String getRight() {
        return this.right;
    }
}

