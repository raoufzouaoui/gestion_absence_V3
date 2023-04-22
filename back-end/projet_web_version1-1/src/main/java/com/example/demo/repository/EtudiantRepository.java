package com.example.demo.repository;


import com.example.demo.entities.Etudiant;
import com.example.demo.entities.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {

    void deleteEtudiantById(Long id);
    Optional<Etudiant> findEtudiantByCin(Long cin);
    Optional<Etudiant> findEtudiantByEmail(String email);
    Optional<Etudiant> findEtudiantById(Long id);

    Etudiant findByEmail(String email);
    List<Etudiant> findEtudiantByGroupe(Groupe groupe);

}
