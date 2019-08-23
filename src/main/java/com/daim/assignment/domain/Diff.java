package com.daim.assignment.domain;

import com.daim.assignment.repositories.models.DiffEntity;

import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

public class Diff {

    private static final String LEFT_AND_RIGHT_HAS_DIFFERENT_SIZES = "Left and Right data has different sizes";
    private static final String LEFT_AND_RIGHT_ARE_EQUAL = "Left and Right data are equal";

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

    public String compareLeftAndRight() {

        if (Objects.nonNull(leftData) && Objects.nonNull(rightData)) {

            byte[] leftByteArray = Base64.getMimeDecoder().decode(getFileData(leftData));
            byte[] rightByteArray = Base64.getMimeDecoder().decode(getFileData(rightData));

            if (leftByteArray.length != rightByteArray.length) {
                return LEFT_AND_RIGHT_HAS_DIFFERENT_SIZES;
            }

            if (Arrays.equals(leftByteArray, rightByteArray)) {
                return LEFT_AND_RIGHT_ARE_EQUAL;
            }

            return getOffsetDifference(leftByteArray, rightByteArray);
        }

        return LEFT_AND_RIGHT_HAS_DIFFERENT_SIZES;
    }

    private String getOffsetDifference(byte[] leftByteArray, byte[] rightByteArray) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Datas are different in positions: ");

        for (int i = 0; i < leftByteArray.length; i++) {

            if (leftByteArray[i] != rightByteArray[i]) {
                stringBuilder.append(i).append(",");
            }
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    private String getFileData(String data) {

        String[] splitData = data.split(",");

        return splitData.length > 1 ? splitData[1] : splitData[0];
    }
}
