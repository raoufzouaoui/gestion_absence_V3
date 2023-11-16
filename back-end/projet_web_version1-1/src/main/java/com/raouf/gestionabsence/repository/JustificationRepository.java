package com.raouf.gestionabsence.repository;

import com.raouf.gestionabsence.entities.Justification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JustificationRepository extends JpaRepository<Justification,Long>{
    Optional<Justification> findJustificationById(Long id);
    Optional<List<Justification>> findJustificationByAbsenceId(Long id);
}

