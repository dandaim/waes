package com.daim.assignment.v1.controllers;

import com.daim.assignment.domain.Diff;
import com.daim.assignment.services.DiffService;
import com.daim.assignment.services.exceptions.DiffNotFoundException;
import com.daim.assignment.v1.controllers.models.DiffRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiffControllerTest {

    private static Long ID = 999L;

    private DiffController diffController;

    @Mock
    private DiffService diffService;

    @Before
    public void setUp() {
        this.diffController = new DiffController(diffService);
    }

    @Test
    public void callDiffServiceToSendLeftData() {

        DiffRequest diffRequest = new DiffRequest("data");

        Diff expectedDiff = new Diff(ID, "data", null);

        diffController.putLeft(ID, diffRequest);

        ArgumentCaptor<Diff> captor = ArgumentCaptor.forClass(Diff.class);

        verify(diffService).saveDiff(captor.capture());

        Diff diff = captor.getValue();

        assertThat(diff).isEqualToComparingFieldByField(expectedDiff);
    }

    @Test
    public void returnCreatedWhenReceivingALeftDataAndServiceReturnsCreated() {

        DiffRequest diffRequest = mock(DiffRequest.class);
        when(diffService.saveDiff(any(Diff.class))).thenReturn(true);

        ResponseEntity<?> response = diffController.putLeft(ID, diffRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void returnOkWhenReceivingALeftDataAndServiceReturnsNotCreated() {

        DiffRequest diffRequest = mock(DiffRequest.class);
        when(diffService.saveDiff(any(Diff.class))).thenReturn(false);

        ResponseEntity<?> response = diffController.putLeft(ID, diffRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void callDiffServiceToSendRightData() {

        DiffRequest diffRequest = new DiffRequest("data");

        Diff expectedDiff = new Diff(ID, null, "data");

        diffController.putRight(ID, diffRequest);

        ArgumentCaptor<Diff> captor = ArgumentCaptor.forClass(Diff.class);

        verify(diffService).saveDiff(captor.capture());

        Diff diff = captor.getValue();

        assertThat(diff).isEqualToComparingFieldByField(expectedDiff);
    }

    @Test
    public void returnCreatedWhenReceivingARightDataAndServiceReturnsCreated() {

        DiffRequest diffRequest = mock(DiffRequest.class);
        when(diffService.saveDiff(any(Diff.class))).thenReturn(true);

        ResponseEntity<?> response = diffController.putRight(ID, diffRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void returnOkWhenReceivingARightDataAndServiceReturnsNotCreated() {

        DiffRequest diffRequest = mock(DiffRequest.class);
        when(diffService.saveDiff(any(Diff.class))).thenReturn(false);

        ResponseEntity<?> response = diffController.putRight(ID, diffRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void returnOkWhenGettingADiffForGivenId() throws DiffNotFoundException {

        Long diffId = 1L;

        ResponseEntity<?> response = diffController.getDiff(diffId);

        verify(diffService).getDiff(diffId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void returnNotFoundWhenDiffServiceRaisesDiffNotFoundException() throws DiffNotFoundException {

        Long diffId = 5L;

        doThrow(DiffNotFoundException.class).when(diffService).getDiff(diffId);

        ResponseEntity<?> response = diffController.getDiff(diffId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}