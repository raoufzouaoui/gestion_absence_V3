package com.raouf.gestionabsence.services;

import com.raouf.gestionabsence.entities.Etudiant;
import com.raouf.gestionabsence.entities.Groupe;
import com.raouf.gestionabsence.entities.User;
import com.raouf.gestionabsence.exception.UserNotFoundException;
import com.raouf.gestionabsence.repository.EtudiantRepository;
import com.raouf.gestionabsence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;

    @Autowired
    public EtudiantService(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;

    }

//    public Etudiant addEtudiant(Etudiant etudiant) {
//        Optional<Etudiant> existingEmail = etudiantRepository.findEtudiantByEmail(etudiant.getEmail());
//        Optional<Etudiant> existingCin = etudiantRepository.findEtudiantByCin(etudiant.getCin());
//        if (existingEmail.isEmpty()) {
//            if (existingCin.isEmpty()) {
//                return etudiantRepository.save(etudiant);
//            } else {
//                throw new IllegalArgumentException("Cin already exists.");
//            }
//        } else {
//            throw new IllegalArgumentException("Email already exists.");
//        }
//
//    }

    public List<Etudiant> findAllEtudiant(){
        return etudiantRepository.findAll();
    }

    public Etudiant updateEtudiant(Etudiant etudiant){
        Optional<Etudiant> existingEtudiant = etudiantRepository.findEtudiantByCin(etudiant.getCin());
        System.out.println(existingEtudiant);
        if(existingEtudiant.isPresent()) {
            Etudiant updatedEtudiant = existingEtudiant.get();
            updatedEtudiant.setFirstname(etudiant.getFirstname());
            updatedEtudiant.setLastname(etudiant.getLastname());
            updatedEtudiant.setEmail(etudiant.getEmail());
            updatedEtudiant.setCin(etudiant.getCin());
            updatedEtudiant.setGroupe(etudiant.getGroupe());
            updatedEtudiant.setDateNaissance(etudiant.getDateNaissance());
            updatedEtudiant.setPassword(etudiant.getPassword());
            updatedEtudiant.setConfirmPassword(etudiant.getConfirmPassword());
            updatedEtudiant.setRole("ETUDIANT");
            return etudiantRepository.save(updatedEtudiant);
        } else {
            throw new IllegalArgumentException("Etudiant not found.");
        }
    }

    public Etudiant findEtudiantById(Long id) {
        return etudiantRepository.findEtudiantById(id)
                .orElseThrow(()-> new Error("Etudiant by id" + id +" was not found"));
    }
    public Etudiant findEtudiantByEmail(String  email) {
        return etudiantRepository.findEtudiantByEmail(email)
                .orElseThrow(()-> new Error("Etudiant by email" + email +" was not found"));
    }
    public List<Etudiant> findEtudiantByGroupe(Groupe groupe) {
        List<Etudiant> etudiants = etudiantRepository.findEtudiantByGroupe(groupe);
        if(etudiants==null){
            throw new IllegalArgumentException("Etudiants not found.");
        }
        return etudiants;
    }

    public void deleteEtudiantById(Long id){
        etudiantRepository.deleteEtudiantById(id);
    }



}
