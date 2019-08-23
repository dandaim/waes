package com.daim.assignment.domain;

import com.daim.assignment.repositories.models.DiffEntity;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class DiffTest {

    @Test
    public void convertDiffToDiffEntity() {

        Long id = 5L;
        String leftData = "left data";
        String rightData = "right data";

        Diff diff = new Diff(id, leftData, rightData);

        DiffEntity diffEntity = diff.toDiffEntity();

        assertThat(diffEntity.getId()).isEqualTo(id);
        assertThat(diffEntity.getLeft()).isEqualTo(leftData);
        assertThat(diffEntity.getRight()).isEqualTo(rightData);
    }

    @Test
    public void returnLeftAndRightAreEqualWhenBothSidesAreEqual() throws IOException {

        InputStream leftStream = this.getClass().getResourceAsStream("/base64_image1.txt");
        InputStream rightStream = this.getClass().getResourceAsStream("/base64_image1.txt");

        String leftData = IOUtils.toString(leftStream, StandardCharsets.UTF_8.name());
        String rightData = IOUtils.toString(rightStream, StandardCharsets.UTF_8.name());

        Long id = 678L;

        Diff diff = new Diff(id, leftData, rightData);

        assertThat(diff.compareLeftAndRight()).isEqualTo("Left and Right data are equal");
    }

    @Test
    public void returnLeftAndRightHasDifferentSizesWhenRightIsNull() throws IOException {

        InputStream leftStream = this.getClass().getResourceAsStream("/base64_image1.txt");

        String leftData = IOUtils.toString(leftStream, StandardCharsets.UTF_8.name());
        String rightData = null;

        Long id = 678L;

        Diff diff = new Diff(id, leftData, rightData);

        assertThat(diff.compareLeftAndRight()).isEqualTo("Left and Right data has different sizes");
    }

    @Test
    public void returnLeftAndRightHasDifferentSizesWhenLeftIsNull() throws IOException {

        InputStream rightStream = this.getClass().getResourceAsStream("/base64_image1.txt");

        String rightData = IOUtils.toString(rightStream, StandardCharsets.UTF_8.name());
        String leftData = null;

        Long id = 678L;

        Diff diff = new Diff(id, leftData, rightData);

        assertThat(diff.compareLeftAndRight()).isEqualTo("Left and Right data has different sizes");
    }

    @Test
    public void returnOffset() throws IOException {

        InputStream leftStream = this.getClass().getResourceAsStream("/base64_image1.txt");
        InputStream rightStream = this.getClass().getResourceAsStream("/base64_image3.txt");

        String leftData = IOUtils.toString(leftStream, StandardCharsets.UTF_8.name());
        String rightData = IOUtils.toString(rightStream, StandardCharsets.UTF_8.name());

        Long id = 678L;

        Diff diff = new Diff(id, leftData, rightData);

        assertThat(diff.compareLeftAndRight()).isEqualTo("Datas are different in positions: 67693");
    }
}
