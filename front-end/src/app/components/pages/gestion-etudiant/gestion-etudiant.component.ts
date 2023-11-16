import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import * as moment from 'moment';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { EtudiantService } from 'src/app/services/etudiant.service';
import { Etudiant } from 'src/app/shared/models/Etudiant';

@Component({
  selector: 'app-gestion-etudiant',
  templateUrl: './gestion-etudiant.component.html',
  styleUrls: ['./gestion-etudiant.component.css']
})
export class GestionEtudiantComponent {

  etudiants: Etudiant[]  ;
  public editEtudiant: Etudiant | undefined;
  public deleteEtudiant: Etudiant | undefined;

  constructor(private etudiantService: EtudiantService) {}

  ngOnInit() {
    console.log("Hello");
    this.etudiantService.getAllEtudiants().pipe(
      catchError(error => {
        if (error.status === 403) {
          // Affichez un message d'erreur à l'utilisateur
          console.error('Accès refusé. Vous n\'avez pas les autorisations nécessaires.');
        } else {
          // Gérez d'autres erreurs ici
          console.error('Une erreur inattendue s\'est produite.');
        }
        return throwError(error);
      })
    ).subscribe(
      (data) => {
        console.log(data);
        this.etudiants = data;
      },
      (error) => {
        console.error("Error occurred:", error); // Use console.error for errors
        if (error.status === 0) {
          console.log("Check CORS or network issues.");
        }
      }
    );
  }


  // public onAddEtudiant(addForm:NgForm):void {
  //   console.log(addForm.value.password);
  //   console.log(addForm.value.confirmPassword);
  //   document.getElementById('add-etudiant-form')?.click();
  //   this.etudiantService.addEtudiant(addForm.value).subscribe(data => {
  //     addForm.reset();
  //     this.etudiantService.getAllEtudiants().subscribe(data => {
  //       this.etudiants=data;
  //     },
  //     error => {
  //       console.log("errorrrrrrrrrrrr");
  //     })
  //   },
  //   (error: HttpErrorResponse) => {
  //     alert(error.message);
  //   });
  // }

  // public onUpdateEtudiant(etudiant: Etudiant): void {
  //   console.log(etudiant);

  //   const dateNaissance = new Date(etudiant.dateNaissance);
  //   // set hours to 0 to avoid any timezone offset issues
  //   dateNaissance.setHours(0, 0, 0, 0);
  //   etudiant.dateNaissance=dateNaissance.toISOString().slice(0, 19).replace("T", " ");
  //   this.etudiantService.updateEtudiant(etudiant).subscribe(data => {
  //     console.log(etudiant);
  //     this.etudiantService.getAllEtudiants().subscribe(data => {
  //       console.log(data);
  //       this.etudiants=data;
  //     },
  //     error => {
  //       console.log("errorrrrrrrrrrrr");
  //     })
  //   },
  //   (error: HttpErrorResponse) => {
  //     alert(error.message);
  //   });
  // }

  // public onDeleteEtudiant(etudiantId:number | any): void {
  //   this.etudiantService.deleteEtudiant(etudiantId).subscribe(
  //     (response: void) => {
  //       this.etudiantService.getAllEtudiants().subscribe(data => {
  //         console.log(data);
  //         this.etudiants=data;
  //       },
  //       error => {
  //         console.log("errorrrrrrrrrrrr");
  //       })
  //     },
  //     (error: HttpErrorResponse) => {
  //       alert(error.message);
  //     }
  //   );
  // }

  // public searchEtudiants(key: string): void {
  //   console.log(key);
  //   const results: Etudiant[] = [];
  //   if(this.etudiants)
  //     for (const etudiant of this.etudiants) {
  //       if (etudiant.cin.toString().toLowerCase().indexOf(key.toString().toLowerCase()) !== -1
  //       || etudiant.firstname.toLowerCase().indexOf(key.toLowerCase()) !== -1
  //       || etudiant.lastname.toLowerCase().indexOf(key.toLowerCase()) !== -1
  //       || etudiant.email.toLowerCase().indexOf(key.toLowerCase()) !== -1
  //       || etudiant.groupe.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
  //         results.push(etudiant);
  //       }
  //     }
  //   this.etudiants = results;
  //   if (results.length === 0 || !key) {
  //     alert("pas des etudiant dans se groupe")
  //     this.etudiantService.getAllEtudiants().subscribe(data => {
  //       console.log(data);
  //       this.etudiants=data;
  //     },
  //     error => {
  //       console.log("errorrrrrrrrrrrr");
  //     })
  //   }
  // }

  public onOpenModal(etudiant: Etudiant | any, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addEtudiantModal');
    }
    if (mode === 'edit') {
      this.editEtudiant = etudiant;
      button.setAttribute('data-target', '#updateEtudiantModal');
    }
    if (mode === 'delete') {
      this.deleteEtudiant = etudiant;
      button.setAttribute('data-target', '#deleteEtudiantModal');
    }
    if(container)
      container.appendChild(button);
    button.click();
  }












}
