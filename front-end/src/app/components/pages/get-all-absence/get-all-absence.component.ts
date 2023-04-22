import { Component } from '@angular/core';
import { AbsenceService } from 'src/app/services/absence.service';
import { EtudiantService } from 'src/app/services/etudiant.service';
import { UserService } from 'src/app/services/user.service';
import { Absence } from 'src/app/shared/models/Absence';
import { Etudiant } from 'src/app/shared/models/Etudiant';

@Component({
  selector: 'app-get-all-absence',
  templateUrl: './get-all-absence.component.html',
  styleUrls: ['./get-all-absence.component.css']
})
export class GetAllAbsenceComponent {
  public absences:Absence[];
  constructor(private absenceService:AbsenceService,private etudiantService:EtudiantService,private userService:UserService){ }

  ngOnInit() {
    const nom=this.userService.currentUser.nom;
    const prenom=this.userService.currentUser.prenom;
    this.absenceService.getAllAbsence().subscribe(data => {
      console.log(data);
      this.absences=data;

    },
    error => {
      console.log("errorrrrrrrrrrrr");
    });
  }

  public searchEtudiants(key: string): void {
    console.log(key);
    const results: Absence[] = [];
    if(this.absences)
      for (const absence of this.absences) {
        if ( absence.etudiant.groupe.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
          results.push(absence);
        }
      }
    this.absences = results;
    if (results.length === 0 || !key) {
      alert("pas des etudiant dans se groupe")
      this.absenceService.getAllAbsence().subscribe(data => {
        console.log(data);
        this.absences=data;
      },
      error => {
        console.log("errorrrrrrrrrrrr");
      })
    }
  }


}
