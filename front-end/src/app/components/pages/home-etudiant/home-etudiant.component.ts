import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AbsenceService } from 'src/app/services/absence.service';
import { EtudiantService } from 'src/app/services/etudiant.service';
import { Absence } from 'src/app/shared/models/Absence';
import { Justification } from 'src/app/shared/models/Justification';
import { ToastrService } from 'ngx-toastr';
import { JustificationService } from 'src/app/services/justification.service';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-home-etudiant',
  templateUrl: './home-etudiant.component.html',
  styleUrls: ['./home-etudiant.component.css']
})
export class HomeEtudiantComponent {
  public absences:Absence[];
  public absence:Absence;
  public justifications:Justification[];
  public email: any;
  public id: number;
  constructor(private absenceService:AbsenceService,private activatedroute:ActivatedRoute,private justificationService:JustificationService, private etudiantService:EtudiantService, private toastrService:ToastrService){ }

  logout(){
    this.etudiantService.logout();
  }

  ngOnInit() {
    this.email = this.etudiantService.currentUser.email;

    this.etudiantService.getEtudiantByEmail(this.email)
      .pipe(
        switchMap(etudiantData => { //switchMap operator is used to switch to a new observable
          this.id = etudiantData.id;
          console.log(this.id);
          return this.absenceService.getAbsencesByIdEtudiant(this.id);
        })
      )
      .subscribe(
        absencesData => {
          this.absences = absencesData;
        },
        error => {
          console.log("errorrrrrrrrrrrr");
        }
      );
  }

  public onAddJustification(addForm:NgForm):void {
    const dated = addForm.value.dated;
    const datef = addForm.value.datef;
    const description = addForm.value.description;
    const id = addForm.form.get(`justification`)?.value;
    console.log(id);
    this.absenceService.getAbsenceById(id).subscribe(data => {
      this.absence=data;
      const justification  = new Justification(dated, datef, description,this.absence);
      document.getElementById('add-justification-form')?.click();
      this.justificationService.addJustification(justification).subscribe(data => {
      console.log(data);
      addForm.reset();
      this.toastrService.success(
        `justification added Successful!`,
      )
    },
    (error: HttpErrorResponse) => {
      alert(error.message);
      this.toastrService.success(
        error.message
      )
    });
    })
  }

  public searchEtudiants(key: string): void {
    console.log(key);
    const results: Absence[] = [];
    if(this.absences)
      for (const absence of this.absences) {
        if ( absence.justification.toLowerCase().indexOf(key.toLowerCase()) !== -1
        || absence.matiere.nom.toLowerCase().indexOf(key.toLowerCase()) !== -1
        || absence.date.toString().toLowerCase().indexOf(key.toLowerCase()) !== -1
        || absence.heure.toString().toLowerCase().indexOf(key.toLowerCase()) !== -1 ) {
          results.push(absence);
        }
      }
    this.absences = results;
    if (results.length === 0 || !key) {
      this.absenceService.getAbsencesByIdEtudiant(this.etudiantService.currentUser.id).subscribe(data => {
        this.absences=data;

      },
      error => {
        console.log("errorrrrrrrrrrrr");
      });
    }
  }

  public onOpenModal( mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addJustificationModal');
    }
    if(container)
      container.appendChild(button);
    button.click();
  }


}
