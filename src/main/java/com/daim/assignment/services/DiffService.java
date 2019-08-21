package com.daim.assignment.services;

import com.daim.assignment.domain.Diff;
import com.daim.assignment.repositories.DiffRepository;
import com.daim.assignment.repositories.models.DiffEntity;
import com.daim.assignment.services.exceptions.DiffNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiffService {

    private DiffRepository diffRepository;

    public DiffService(DiffRepository diffRepository) {
        this.diffRepository = diffRepository;
    }

    public boolean saveDiff(Diff diff) {

        Optional<DiffEntity> returnedDiffEntity = findDiffEntity(diff.getId());

        DiffEntity diffEntity;
        boolean diffCreated = false;

        if (returnedDiffEntity.isPresent()) {
            diffEntity = new DiffEntity(returnedDiffEntity.get(), diff);
        } else {
            diffEntity = diff.toDiffEntity();
            diffCreated = true;
        }

        diffRepository.save(diffEntity);

        return diffCreated;
    }

    private Optional<DiffEntity> findDiffEntity(Long id) {
        return diffRepository.findById(id);
    }

    public Diff getDiff(Long diffId) throws DiffNotFoundException {

        Optional<DiffEntity> optionalDiffEntity = findDiffEntity(diffId);

        if (optionalDiffEntity.isPresent()) {
            DiffEntity diffEntity = optionalDiffEntity.get();
            return new Diff(diffEntity.getId(), diffEntity.getLeft(), diffEntity.getRight());
        }

        throw new DiffNotFoundException();
    }
}
