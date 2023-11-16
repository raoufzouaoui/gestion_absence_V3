package com.raouf.gestionabsence.services;

import com.raouf.gestionabsence.entities.Absence;
import com.raouf.gestionabsence.entities.Enseignant;
import com.raouf.gestionabsence.entities.Etudiant;
import com.raouf.gestionabsence.repository.AbsenceRepository;
import com.raouf.gestionabsence.repository.EnseignantRepository;
import com.raouf.gestionabsence.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbsenceService {
    private final AbsenceRepository absenceRepository;

    private final EnseignantRepository enseignantRepository;
    private final EtudiantRepository etudiantRepository ;

    @Autowired
    public AbsenceService(AbsenceRepository absenceRepository,
                          EtudiantRepository etudiantRepository,
                          EnseignantRepository enseignantRepository) {
        this.absenceRepository = absenceRepository;
        this.etudiantRepository = etudiantRepository;
        this.enseignantRepository = enseignantRepository;
    }

    public Absence[] addAbsence(Absence[] absences) {
        for (int i = 0; i < absences.length; i++) {
            System.out.println(absences[i]);
            Etudiant etudiant = etudiantRepository.findById(absences[i].getEtudiant().getId()).orElse(null);
            System.out.println(etudiant);
            Enseignant enseignant = enseignantRepository.findById(absences[i].getEnseignant().getId()).orElse(null);
            System.out.println(enseignant);
            if (etudiant != null ) {
                absences[i].setEnseignant(enseignant);
                absences[i].setEtudiant(etudiant);
                absenceRepository.save(absences[i]);
            } else {
                // handle case where etudiant is not found
            }
        }
        return absences;
    }

    public Absence updateAbsence(Absence absence){
        Optional<Absence> existingAbsence = absenceRepository.findAbsenceById(absence.getId());
        System.out.println(existingAbsence);
        if(existingAbsence.isPresent()) {
            Absence updatedAbsence = existingAbsence.get();
            updatedAbsence.setEnseignant(absence.getEnseignant());
            updatedAbsence.setJustification(absence.getJustification());
            updatedAbsence.setJustifie(absence.getJustifie());
            return absenceRepository.save(updatedAbsence);
        } else {
            throw new IllegalArgumentException("Absence not founddddddddddddd.");
        }
    }

    public Absence findAbsenceById(Long id) {
        return absenceRepository.findAbsenceById(id)
                .orElseThrow(()-> new Error("Absence by id" + id +" was not found"));
    }
    public List<Absence> findAllAbsence(){
        return absenceRepository.findAll();
    }

    public Optional<List<Absence>> findAbsenceByEtudiant(Etudiant etudiant){
        System.out.println("etudiant");
        System.out.println(etudiant);
        return absenceRepository.findAbsenceByEtudiant(etudiant);
    }

}
