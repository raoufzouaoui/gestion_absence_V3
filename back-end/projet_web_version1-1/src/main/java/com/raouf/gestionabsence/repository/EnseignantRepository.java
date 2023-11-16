package com.raouf.gestionabsence.repository;

import com.raouf.gestionabsence.entities.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant,Long> {

    Enseignant findByEmail(String email);

    Optional<Enseignant> findById(Long id);
}
