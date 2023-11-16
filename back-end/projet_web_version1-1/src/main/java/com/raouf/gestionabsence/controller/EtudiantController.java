package com.raouf.gestionabsence.controller;


import com.raouf.gestionabsence.entities.Absence;
import com.raouf.gestionabsence.entities.Etudiant;
import com.raouf.gestionabsence.entities.Groupe;
import com.raouf.gestionabsence.repository.AbsenceRepository;
import com.raouf.gestionabsence.repository.EtudiantRepository;
import com.raouf.gestionabsence.services.AbsenceService;
import com.raouf.gestionabsence.services.EtudiantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/etudiant")
public class EtudiantController {

    private final EtudiantService etudiantService;


    public EtudiantController(EtudiantService etudiantService) {
        super();
        this.etudiantService = etudiantService;
    }

//    @PostMapping("/login")
//    public ResponseEntity<Etudiant> loginUser(@RequestBody Etudiant etudiant) {
//        Etudiant user = etudiantRepository.findByEmail(etudiant.getEmail());
//        if(user.getPassword().equals(etudiant.getPassword())){
//            return ResponseEntity.ok(user);
//        }
//        return (ResponseEntity<Etudiant>) ResponseEntity.internalServerError();
//    }



    @PreAuthorize("hasRole('ENSEIGNANT')")
    @GetMapping("/getAllEtudiant")
    public ResponseEntity<List<Etudiant>> getAllEtudiant () {
        List<Etudiant> etudiants = etudiantService.findAllEtudiant();
        return new ResponseEntity<>(etudiants, HttpStatus.OK);
    }

    @GetMapping("/findEtudiant/byId/{id}")
    public ResponseEntity<Etudiant> getEtudiantById (@PathVariable("id") Long id) {
        Etudiant etudiant = etudiantService.findEtudiantById(id);
        if(etudiant == null){
            throw new IllegalArgumentException("id not found");
        }
        return new ResponseEntity<>(etudiant, HttpStatus.OK);
    }

    @GetMapping("/findEtudiant/byEmail/{email}")
    public ResponseEntity<Etudiant> getEtudiantByEmail (@PathVariable("email") String  email) {
        Etudiant etudiant = etudiantService.findEtudiantByEmail(email);
        if(etudiant == null){
            throw new IllegalArgumentException("Etudiant not found");
        }
        return new ResponseEntity<>(etudiant, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ENSEIGNANT')")
    @GetMapping("/findEtudiant/byGroupe/{groupe}")
    public ResponseEntity<List<Etudiant>> getEtudiantByGroupe (@PathVariable("groupe") Groupe groupe) {
        List<Etudiant> etudiants = etudiantService.findEtudiantByGroupe(groupe);
        if(etudiants == null){
            throw new IllegalArgumentException("non Etudiant trouvé");
        }
        return new ResponseEntity<>(etudiants, HttpStatus.OK);
    }

//    @PostMapping("/add")
//    public ResponseEntity<Etudiant> addEtudiant (@RequestBody Etudiant etudiant) {
//        Etudiant newEtudiant = etudiantService.addEtudiant(etudiant);
//        System.out.println(etudiant);
//        return new ResponseEntity<>(newEtudiant, HttpStatus.CREATED);
//    }
    @PreAuthorize("hasRole('ENSEIGNANT')")
    @PutMapping("/update")
    public ResponseEntity<Etudiant> updateEtudiant (@RequestBody Etudiant etudiant) {
        Etudiant updateEtudiant = etudiantService.updateEtudiant(etudiant);
        return new ResponseEntity<>(updateEtudiant, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ENSEIGNANT')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEtudiantById(@PathVariable("id") Long id) {
        etudiantService.deleteEtudiantById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
