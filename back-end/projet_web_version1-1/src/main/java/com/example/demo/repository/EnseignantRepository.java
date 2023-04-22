package com.example.demo.repository;

import com.example.demo.entities.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant,Long> {

    Enseignant findByEmail(String email);

    Optional<Enseignant> findById(Long id);
}
