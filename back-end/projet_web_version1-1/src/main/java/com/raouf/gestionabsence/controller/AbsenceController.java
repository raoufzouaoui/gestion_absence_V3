package com.raouf.gestionabsence.controller;


import com.raouf.gestionabsence.entities.Absence;
import com.raouf.gestionabsence.entities.Etudiant;
import com.raouf.gestionabsence.repository.AbsenceRepository;
import com.raouf.gestionabsence.repository.EtudiantRepository;
import com.raouf.gestionabsence.services.AbsenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/absence")
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

    @PreAuthorize("hasRole('ENSEIGNANT')")
    @PostMapping("/addAbsence")
    public ResponseEntity<Absence[]> addAbsence (@RequestBody Absence[] absences) {
        Absence[] newAbsences = absenceService.addAbsence(absences);
        return new ResponseEntity<>(newAbsences, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ENSEIGNANT')")
    @GetMapping("/getAllAbsence")
    public ResponseEntity<List<Absence>> getAllAbsence () {
        List<Absence> absences = absenceService.findAllAbsence();
        return new ResponseEntity<>(absences, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ETUDIANT')")
    @GetMapping("/findAbsence/byId/{id}")
    public ResponseEntity<Absence> getAbsenceById(@PathVariable("id") Long id) {
        Absence absence = absenceRepository.findAbsenceById(id)
                .orElseThrow(() -> new IllegalArgumentException("Absence not found"));
        return new ResponseEntity<>(absence, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ETUDIANT')")
    @GetMapping("/findAbsence/byEtudiantId/{etudiantId}")
    public ResponseEntity<List<Absence>> getAbsenceByEtudiant(@PathVariable("etudiantId") Long id) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("etudiant not found"));

        Optional<List<Absence>> absences = absenceService.findAbsenceByEtudiant(etudiant);
        List<Absence> absenceList = absences.orElseThrow(() -> new IllegalArgumentException("no absences found"));
        return new ResponseEntity<>(absenceList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ENSEIGNANT')")
    @PutMapping("/updateAbsence")
    public ResponseEntity<Absence> updateAbsence (@RequestBody Absence absence) {
        Absence updateAbsence = absenceService.updateAbsence(absence);
        System.out.println(absence);
        return new ResponseEntity<>(updateAbsence, HttpStatus.OK);
    }



}
