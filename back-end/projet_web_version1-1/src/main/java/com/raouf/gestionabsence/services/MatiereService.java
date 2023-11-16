package com.raouf.gestionabsence.services;

import com.raouf.gestionabsence.entities.Matiere;
import com.raouf.gestionabsence.repository.MatiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MatiereService {

    private final MatiereRepository matiereRepository;

    @Autowired
    public MatiereService(MatiereRepository matiereRepository) {
        this.matiereRepository = matiereRepository;
    }

    public Matiere findMatiereById(Long id) {
        return matiereRepository.findMatiereById(id)
                .orElseThrow(()-> new Error("Matiere by id" + id +" was not found"));
    }

    public Matiere findMatiereByNom(String  nom) {
        return matiereRepository.findMatiereByNom(nom)
                .orElseThrow(()-> new Error("Matiere by nom" + nom +" was not found"));
    }

}
