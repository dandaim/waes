package com.daim.assignment.repositories.models;

import com.daim.assignment.domain.Diff;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DiffEntityTest {

    @Test
    public void createDiffEntityWithAllArgs() {

        Long id = 999L;
        String left = "left";
        String right = "right";

        DiffEntity diffEntity = new DiffEntity(id, left, right);

        assertThat(diffEntity.getId()).isEqualTo(id);
        assertThat(diffEntity.getLeft()).isEqualTo(left);
        assertThat(diffEntity.getRight()).isEqualTo(right);
    }

    @Test
    public void createDiffEntityGettingAllValuesFromDiffObject() {
        Long id = 999L;
        String left = "left";
        String right = "right";

        Diff diff = new Diff(id, left, right);
        DiffEntity mockedDiffEntity = new DiffEntity(id, null, null);

        DiffEntity diffEntity = new DiffEntity(mockedDiffEntity, diff);

        assertThat(diffEntity.getId()).isEqualTo(diff.getId());
        assertThat(diffEntity.getLeft()).isEqualTo(diff.getLeftData());
        assertThat(diffEntity.getRight()).isEqualTo(diff.getRightData());
    }

    @Test
    public void keepDiffEntityValuesWhenDiffHasNullValuesOnRightAndLeftData() {
        Long id = 999L;
        String left = "left";
        String right = "right";

        Diff diff = new Diff(id, null, null);
        DiffEntity expectedDiffEntity = new DiffEntity(id, left, right);

        DiffEntity diffEntity = new DiffEntity(expectedDiffEntity, diff);

        assertThat(diffEntity.getId()).isEqualTo(diff.getId());
        assertThat(diffEntity.getLeft()).isEqualTo(expectedDiffEntity.getLeft());
        assertThat(diffEntity.getRight()).isEqualTo(expectedDiffEntity.getRight());
    }

}
