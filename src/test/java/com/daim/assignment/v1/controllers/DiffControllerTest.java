package com.daim.assignment.v1.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DiffControllerTest {

    private DiffController diffController;

    @Before
    public void setUp() {
        this.diffController = new DiffController();
    }

    @Test
    public void returnCreatedWhenReceivingALeftData() {

        ResponseEntity<?> response = diffController.putLeft();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void returnCreatedWhenReceivingARightData() {

        ResponseEntity<?> response = diffController.putRight();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void returnOkWhenGettingADiffForGivenId() {

        Long diffId = 1L;

        ResponseEntity<?> response = diffController.getDiff(diffId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}