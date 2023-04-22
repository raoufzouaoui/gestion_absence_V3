package com.example.demo.controller;


import com.example.demo.entities.Absence;
import com.example.demo.entities.Etudiant;
import com.example.demo.repository.AbsenceRepository;
import com.example.demo.repository.EtudiantRepository;
import com.example.demo.services.AbsenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/etudiant/absence")
public class AbsenceController {


    private final AbsenceService absenceService;
    private final AbsenceRepository absenceRepository;
    private final EtudiantRepository etudiantRepository;

    public AbsenceController(AbsenceService absenceService,EtudiantRepository etudiantRepository,AbsenceRepository absenceRepository) {
        super();
        this.absenceService = absenceService;
        this.etudiantRepository=etudiantRepository;
        this.absenceRepository=absenceRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<Absence[]> addAbsence (@RequestBody Absence[] absences) {
        Absence[] newAbsences = absenceService.addAbsence(absences);
        return new ResponseEntity<>(newAbsences, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Absence>> getAllAbsence () {
        List<Absence> absences = absenceService.findAllAbsence();
        return new ResponseEntity<>(absences, HttpStatus.OK);
    }

    @GetMapping("/find/byId/{id}")
    public ResponseEntity<Absence> getAbsenceById(@PathVariable("id") Long id) {
        Absence absence = absenceRepository.findAbsenceById(id)
                .orElseThrow(() -> new IllegalArgumentException("Absence not found"));
        return new ResponseEntity<>(absence, HttpStatus.OK);
    }

    @GetMapping("/find/byEtudiant/{etudiant}")
    public ResponseEntity<List<Absence>> getAbsenceByEtudiant(@PathVariable("etudiant") Long id) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Absence not found"));

        Optional<List<Absence>> absences = absenceService.findAbsenceByEtudiant(etudiant);
        List<Absence> absenceList = absences.orElseThrow(() -> new IllegalArgumentException("no absences found"));
        return new ResponseEntity<>(absenceList, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Absence> updateEtudiant (@RequestBody Absence absence) {
        Absence updateAbsence = absenceService.updateAbsence(absence);
        System.out.println(absence);
        return new ResponseEntity<>(updateAbsence, HttpStatus.OK);
    }



}
