package com.daim.assignment.services;

import com.daim.assignment.domain.Diff;
import com.daim.assignment.repositories.DiffRepository;
import com.daim.assignment.repositories.models.DiffEntity;
import com.daim.assignment.services.exceptions.DiffNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiffServiceTest {

    @Mock
    private DiffRepository diffRepository;

    private DiffService diffService;

    @Before
    public void setUp() {
        this.diffService = new DiffService(diffRepository);
    }

    @Test
    public void callDiffRepositoryToFindDiffEntityBeforeSaveDiffLeft() {

        Long id = 2L;

        Diff diff = new Diff(id, "leftData", "");

        diffService.saveDiff(diff);

        verify(diffRepository).findById(id);
    }

    @Test
    public void returnCreatedFalseWhenDiffAlreadyExistsInDatabase() {

        Long id = 2L;
        Diff diff = new Diff(id, "leftData", "rightData");
        DiffEntity diffEntity = new DiffEntity(diff.getId(), diff.getLeftData(), diff.getRightData());

        when(diffRepository.findById(id)).thenReturn(Optional.of(diffEntity));

        boolean diffCreated = diffService.saveDiff(diff);

        ArgumentCaptor<DiffEntity> argumentCaptor = ArgumentCaptor.forClass(DiffEntity.class);

        verify(diffRepository).save(argumentCaptor.capture());

        DiffEntity savedEntity = argumentCaptor.getValue();

        assertThat(diffCreated).isFalse();

        assertThat(savedEntity.getId()).isEqualTo(diff.getId());
        assertThat(savedEntity.getLeft()).isEqualTo(diff.getLeftData());
        assertThat(savedEntity.getRight()).isEqualTo(diff.getRightData());
    }

    @Test
    public void returnCreatedTrueWhenDiffAlreadyExistsInDatabase() {

        Long id = 3L;
        Diff diff = new Diff(id, "leftData", "rightData");

        when(diffRepository.findById(id)).thenReturn(Optional.empty());

        boolean diffCreated = diffService.saveDiff(diff);

        ArgumentCaptor<DiffEntity> argumentCaptor = ArgumentCaptor.forClass(DiffEntity.class);

        verify(diffRepository).save(argumentCaptor.capture());

        DiffEntity savedEntity = argumentCaptor.getValue();

        assertThat(diffCreated).isTrue();

        assertThat(savedEntity.getId()).isEqualTo(diff.getId());
        assertThat(savedEntity.getLeft()).isEqualTo(diff.getLeftData());
        assertThat(savedEntity.getRight()).isEqualTo(diff.getRightData());
    }

    @Test
    public void throwDiffNotFoundExceptionWhenDiffIsNotPresentInDatabase() throws DiffNotFoundException {

        Long id = 666L;

        assertThatThrownBy(() -> diffService.getDiff(id))
                .isInstanceOf(DiffNotFoundException.class);
    }

    @Test
    public void returnDiffWhenItIsPresentInDatabase() throws DiffNotFoundException {
        Long id = 2019L;
        Diff expectedDiff = new Diff(id, "left", "right");
        DiffEntity diffEntity = new DiffEntity(expectedDiff.getId(), expectedDiff.getLeftData(), expectedDiff.getRightData());

        when(diffRepository.findById(id)).thenReturn(Optional.of(diffEntity));

        Diff diff = diffService.getDiff(id);

        verify(diffRepository).findById(id);

        assertThat(diff).isEqualToComparingFieldByField(expectedDiff);
    }
}
