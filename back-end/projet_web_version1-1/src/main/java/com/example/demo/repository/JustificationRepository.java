package com.example.demo.repository;

import com.example.demo.entities.Absence;
import com.example.demo.entities.Justification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JustificationRepository extends JpaRepository<Justification,Long>{
    Optional<Justification> findJustificationById(Long id);
    Optional<List<Justification>> findJustificationByAbsenceId(Long id);
}

