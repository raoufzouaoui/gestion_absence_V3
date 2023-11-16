package com.raouf.gestionabsence.controller;

import com.raouf.gestionabsence.repository.EnseignantRepository;
import com.raouf.gestionabsence.repository.JustificationRepository;
import com.raouf.gestionabsence.services.AbsenceService;
import com.raouf.gestionabsence.services.JustificationService;
import com.raouf.gestionabsence.entities.Absence;
import com.raouf.gestionabsence.entities.Justification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/justification")
public class JustificationController {

    private final JustificationService justificationService;

    private final AbsenceService absenceService;
    private final JustificationRepository justificationRepository;
    private final EnseignantRepository enseignantRepository;
    private static final Logger logger = LoggerFactory.getLogger(JustificationController.class);

    public JustificationController(JustificationService justificationService,
                                   AbsenceService absenceService,
                                   EnseignantRepository enseignantRepository,
                                   JustificationRepository justificationRepository) {
        super();
        this.justificationService = justificationService;
        this.justificationRepository = justificationRepository;
        this.absenceService = absenceService;
        this.enseignantRepository = enseignantRepository;
    }

    @PreAuthorize("hasRole('ETUDIANT')")
    @PostMapping("/addJustification")
    public ResponseEntity<Justification> addJustification (@RequestBody Justification justification) {
        Justification newJustification = justificationService.addJustification(justification);
        logger.info("Justification added: {}", newJustification);
        return new ResponseEntity<>(newJustification, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ENSEIGNANT')")
    @GetMapping("/getAllJustification")
    public ResponseEntity<List<Justification>> getAllJustification () {
        List<Justification> justification = justificationService.findAllJustification();
        return new ResponseEntity<>(justification, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ETUDIANT')")
    @GetMapping("/findJustification/byAbsenceId/{absence}")
    public ResponseEntity<List<Justification>> getJustificationsByAbsenceId(@PathVariable("absence") Absence absence) {
        Optional<List<Justification>> absences = justificationService.findJustificationByIdAbsence(absence);
        if (absences.isPresent()) {
            return new ResponseEntity<>(absences.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
    }
    @PreAuthorize("hasRole('ENSEIGNANT')")
    @GetMapping("/findJustification/ByEnseignantId/{enseignantId}")
    public ResponseEntity<List<Justification>> getJustificationsByEnseignantId(@PathVariable("enseignantId") Long enseignantId) {
        // Retrieve all justifications from the database
        List<Justification> justification = justificationService.findAllJustification();
        List<Justification> justificationsByEnseignantId = new ArrayList<>();
        for(int i = 0; i < justification.size(); i++) {
            Justification j = justification.get(i);
            Absence a = absenceService.findAbsenceById(j.getAbsence().getId());
            if (a != null && a.getEnseignant().getId().equals(enseignantId)) {
                justificationsByEnseignantId.add(j);
            }
        }
        return new ResponseEntity<>(justificationsByEnseignantId.stream().filter(Objects::nonNull).collect(Collectors.toList()), HttpStatus.OK);

    }



}
