import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, NgForm } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { forkJoin, map } from 'rxjs';
import { AbsenceService } from 'src/app/services/absence.service';
import { EtudiantService } from 'src/app/services/etudiant.service';
import { UserService } from 'src/app/services/user.service';
import { Absence } from 'src/app/shared/models/Absence';
import { Etudiant } from 'src/app/shared/models/Etudiant';
import { Matiere } from 'src/app/shared/models/Matiere';
import { User } from 'src/app/shared/models/User';
@Component({
  selector: 'app-add-absence',
  templateUrl: './add-absence.component.html',
  styleUrls: ['./add-absence.component.css']
})
export class AddAbsenceComponent {
  public students: Etudiant[] ;
  public absences: Absence[] = [];
  public matiere : Matiere;
  public enseignant : User;
  constructor(private http: HttpClient,private absenceService:AbsenceService,private toastrService:ToastrService,private etudiantService:EtudiantService,private userService:UserService) {}

  ngOnInit() {
    this.etudiantService.getAllEtudiants().subscribe(data => {
      console.log(data);
      this.students=data;
    },
    error => {
      console.log("errorrrrrrrrrrrr");
    });
  }



  public onAddAbsence(addForm:NgForm):void {
    console.log(addForm.value);
    // Get form values
    const date = addForm.value.date;
    const heure = addForm.value.heure;
    const groupe = addForm.value.key;
    const nomMatiere = addForm.value.matiere;

    this.absenceService.getMatiereByNom(nomMatiere).subscribe(data => {
      this.matiere=data;
      // console.log(this.matiere);

      // console.log(this.students);
      var justification="";
      var justifie="";
      for(let i=0;i<this.students.length;i++){
        const studentId=this.students[i].id;
        const control = addForm.form.get(`justification_${studentId}`);
        console.log(`id: ${studentId}`);
        // console.log(this.students[i]);
        if (control !== null) {
          justification=control.value;
        }
        if(justification==="A"){
          justifie="Non";
        }
         this.enseignant = this.userService.currentUser;
        console.log(this.enseignant);
        const absence  = new Absence(date, heure, this.matiere,this.students[i],justification,justifie,this.enseignant);
        // console.log(absence);
        this.absences.push(absence);
      }
      this.absenceService.addAbsence(this.absences).subscribe(data => {
        console.log(data);
        this.toastrService.success(
          `absences valide!`,
        )
      },
      (error: HttpErrorResponse) => {
        this.toastrService.success(
          error.message,
        )
      });

    });
  }



public searchEtudiants(key: string): void {
  console.log(key);
  const results: Etudiant[] = [];
  if(this.students)
    for (const etudiant of this.students) {
      if (etudiant.groupe.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        if(etudiant.groupe)
        results.push(etudiant);
      }
    }
  this.students = results;
  if (results.length === 0 || !key) {
    alert("pas des etudiant dans se groupe")
    this.etudiantService.getAllEtudiants().subscribe(data => {
      console.log(data);
      this.students=data;
    },
    error => {
      console.log("errorrrrrrrrrrrr");
    })
  }
}






















}
