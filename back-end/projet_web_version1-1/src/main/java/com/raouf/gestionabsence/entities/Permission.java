package com.raouf.gestionabsence.entities;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ENSEIGNANT_READ("enseignant:read"),
    ENSEIGNANT_UPDATE("enseignant:update"),
    ENSEIGNANT_CREATE("enseignant:create"),
    ENSEIGNANT_DELETE("enseignant:delete"),
    ETUDIANT_READ("etudiant:read"),
    ETUDIANT_UPDATE("etudiant:update"),
    ETUDIANT_CREATE("etudiant:create"),
    ETUDIANT_DELETE("etudiant:delete")

    ;

    @Getter
    private final String permission;
}
