package com.daim.assignment.repositories;

import com.daim.assignment.repositories.models.DiffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiffRepository extends JpaRepository<DiffEntity, Long> {
}
