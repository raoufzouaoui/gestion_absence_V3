package com.example.demo.controller;

import com.example.demo.entities.Matiere;
import com.example.demo.services.MatiereService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matiere")
public class MatiereController {

    private final MatiereService matiereService;

    public MatiereController(MatiereService matiereService) {
        super();
        this.matiereService = matiereService;
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<Matiere> getMatiereById (@PathVariable("id") Long id) {
        Matiere matiere = matiereService.findMatiereById(id);
        if(matiere == null){
            throw new IllegalArgumentException("matiereid not found");
        }
        return new ResponseEntity<>(matiere, HttpStatus.OK);
    }
    @GetMapping("/find/nom/{nom}")
    public ResponseEntity<Matiere> getMatiereByNom (@PathVariable("nom") String  nom) {
        Matiere matiere = matiereService.findMatiereByNom(nom);
        if(matiere == null){
            throw new IllegalArgumentException("nom non trouvé");
        }
        return new ResponseEntity<>(matiere, HttpStatus.OK);
    }
}
