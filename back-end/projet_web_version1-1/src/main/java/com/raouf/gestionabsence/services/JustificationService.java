package com.raouf.gestionabsence.services;

import com.raouf.gestionabsence.entities.Absence;
import com.raouf.gestionabsence.entities.Justification;
import com.raouf.gestionabsence.repository.JustificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JustificationService {

    private final JustificationRepository justificationRepository;

    @Autowired
    public JustificationService(JustificationRepository justificationRepository) {
        this.justificationRepository=justificationRepository;
    }

    public Justification addJustification(Justification justification) {
        return justificationRepository.save(justification);
    }

    public List<Justification> findAllJustification(){
        return justificationRepository.findAll();
    }

    public Justification findJustificationById(Long id) {
        return justificationRepository.findJustificationById(id)
                .orElseThrow(()-> new Error("Justification by id" + id +" was not found"));
    }

    public Optional<List<Justification>> findJustificationByIdAbsence(Absence absence) {
        Optional<List<Justification>> absences = justificationRepository.findJustificationByAbsenceId(absence.getId());
        if(absences==null){
            throw new IllegalArgumentException("Justification not found.");
        }
        return absences;
    }






}
