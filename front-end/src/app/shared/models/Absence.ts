import { Etudiant } from "./Etudiant";
import { Matiere } from "./Matiere";
import { User } from "./User";

export class Absence{
  id!:number;
  heure!:Date;
  date!:Date;
  matiere!:Matiere;
  etudiant!:Etudiant;
  justification!:string;
  justifie!:string;
  enseignant!:User;

  constructor(date: Date, heure: Date, matiere: Matiere,  etudiant:Etudiant, justification: string,justifie:string,enseignant:User) {
    this.date = date;
    this.heure = heure;
    this.matiere = matiere;
    this.etudiant = etudiant;
    this.justification = justification;
    this.justifie = justifie;
    this.enseignant = enseignant;
  }

}


