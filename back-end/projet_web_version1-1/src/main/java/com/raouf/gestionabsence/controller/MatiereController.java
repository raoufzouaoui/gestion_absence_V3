package com.raouf.gestionabsence.controller;

import com.raouf.gestionabsence.entities.Matiere;
import com.raouf.gestionabsence.services.MatiereService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/matiere")
public class MatiereController {

    private final MatiereService matiereService;

    public MatiereController(MatiereService matiereService) {
        super();
        this.matiereService = matiereService;
    }

    @PreAuthorize("hasRole('ENSEIGNANT')")
    @GetMapping("/findMatiere/byId/{id}")
    public ResponseEntity<Matiere> getMatiereById(@PathVariable("id") Long id) {
        Matiere matiere = matiereService.findMatiereById(id);
        return matiere != null ?
                new ResponseEntity<>(matiere, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ENSEIGNANT')")
    @GetMapping("/findMatiere/byNom/{nom}")
    public ResponseEntity<Matiere> getMatiereByNom(@PathVariable("nom") String nom) {
        Matiere matiere = matiereService.findMatiereByNom(nom);
        return matiere != null ?
                new ResponseEntity<>(matiere, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }
}
