package com.raouf.gestionabsence.entities;

import jakarta.persistence.*;

@Entity
@Table(name="mail")

public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
      private long id;
      private String destinataire;
      private String objet;
      private String message;

    public Mail(long id, String destinataire, String objet, String message) {
        this.id = id;
        this.destinataire = destinataire;
        this.objet = objet;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public String getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "id=" + id +
                ", destinataire='" + destinataire + '\'' +
                ", objet='" + objet + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
