package com.raouf.gestionabsence.repository;

import com.raouf.gestionabsence.entities.Absence;
import com.raouf.gestionabsence.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence,Long> {
    Optional<Absence> findAbsenceById(Long id);
    Optional <List<Absence>> findAbsenceByEtudiant(Etudiant etudiant);
}
