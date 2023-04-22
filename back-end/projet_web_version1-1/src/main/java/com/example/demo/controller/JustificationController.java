package com.example.demo.controller;

import com.example.demo.entities.*;
import com.example.demo.repository.EnseignantRepository;
import com.example.demo.repository.JustificationRepository;
import com.example.demo.services.AbsenceService;
import com.example.demo.services.JustificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/justification")
public class JustificationController {

    private final JustificationService justificationService;

    private final AbsenceService absenceService;
    private final JustificationRepository justificationRepository;
    private final EnseignantRepository enseignantRepository;

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

    @PostMapping("/add")
    public ResponseEntity<Justification> addJustification (@RequestBody Justification justification) {
        Justification newJustification = justificationService.addJustification(justification);
        System.out.println(justification);
        return new ResponseEntity<>(newJustification, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Justification>> getAllJustification () {
        List<Justification> justification = justificationService.findAllJustification();
        return new ResponseEntity<>(justification, HttpStatus.OK);
    }
    @GetMapping("/find/byAbsenceId/{absence}")
    public ResponseEntity<Optional<List<Justification>>> getJustificationByAbsenceId (@PathVariable("absence") Absence absence) {
        Optional<List<Justification>> absences = justificationService.findJustificationByIdAbsence(absence);
        if(absences == null){
            throw new IllegalArgumentException("non Justification trouvé");
        }
        return new ResponseEntity<>(absences, HttpStatus.OK);
    }

    @GetMapping("/find/ByEnseignantId/{enseignantId}")
    public ResponseEntity<List<Justification>> getJustificationByEnseignantId(@PathVariable("enseignantId") Long enseignantId) {
        // Retrieve all justifications from the database
        List<Justification> justification = justificationService.findAllJustification();
        List<Justification> justificationsByEnseignantId = new ArrayList<>();
        for(int i = 0; i < justification.size(); i++) {
            Justification j = justification.get(i);
            Absence a = absenceService.findAbsenceById(j.getAbsence().getId());
            if (a != null && a.getEnseignant().getId().equals(enseignantId)) {
                justificationsByEnseignantId.add(Optional.of(j).orElse(null));
            }
        }
        return new ResponseEntity<>(justificationsByEnseignantId.stream().filter(Objects::nonNull).collect(Collectors.toList()), HttpStatus.OK);

    }

}
