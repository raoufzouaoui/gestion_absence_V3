package com.raouf.gestionabsence.repository;

import com.raouf.gestionabsence.entities.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {

    Optional<Matiere> findMatiereById(Long id);

    Optional<Matiere> findMatiereByNom(String nom);
}
