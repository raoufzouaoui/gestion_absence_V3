import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import * as moment from 'moment';
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
    this.etudiantService.getAllEtudiants().subscribe(data => {
      console.log(data);
      this.etudiants=data;

    },
    error => {
      console.log("errorrrrrrrrrrrr");
    });
  }


  public onAddEtudiant(addForm:NgForm):void {
    document.getElementById('add-etudiant-form')?.click();
    this.etudiantService.addEtudiant(addForm.value).subscribe(data => {
      addForm.reset();
      this.etudiantService.getAllEtudiants().subscribe(data => {
        this.etudiants=data;
      },
      error => {
        console.log("errorrrrrrrrrrrr");
      })
    },
    (error: HttpErrorResponse) => {
      alert(error.message);
    });
  }

  public onUpdateEtudiant(etudiant: Etudiant): void {
    console.log(etudiant);
    etudiant.date_naissance = new Date(etudiant.date_naissance);
    // set hours to 0 to avoid any timezone offset issues
    etudiant.date_naissance.setHours(0, 0, 0, 0);

    this.etudiantService.updateEtudiant(etudiant).subscribe(data => {
      console.log(etudiant);
      this.etudiantService.getAllEtudiants().subscribe(data => {
        console.log(data);
        this.etudiants=data;
      },
      error => {
        console.log("errorrrrrrrrrrrr");
      })
    },
    (error: HttpErrorResponse) => {
      alert(error.message);
    });
  }

  public onDeleteEtudiant(etudiantId:number | any): void {
    this.etudiantService.deleteEtudiant(etudiantId).subscribe(
      (response: void) => {
        this.etudiantService.getAllEtudiants().subscribe(data => {
          console.log(data);
          this.etudiants=data;
        },
        error => {
          console.log("errorrrrrrrrrrrr");
        })
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public searchEtudiants(key: string): void {
    console.log(key);
    const results: Etudiant[] = [];
    if(this.etudiants)
      for (const etudiant of this.etudiants) {
        if (etudiant.cin.toString().toLowerCase().indexOf(key.toString().toLowerCase()) !== -1
        || etudiant.nom.toLowerCase().indexOf(key.toLowerCase()) !== -1
        || etudiant.prenom.toLowerCase().indexOf(key.toLowerCase()) !== -1
        || etudiant.email.toLowerCase().indexOf(key.toLowerCase()) !== -1
        || etudiant.groupe.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
          results.push(etudiant);
        }
      }
    this.etudiants = results;
    if (results.length === 0 || !key) {
      alert("pas des etudiant dans se groupe")
      this.etudiantService.getAllEtudiants().subscribe(data => {
        console.log(data);
        this.etudiants=data;
      },
      error => {
        console.log("errorrrrrrrrrrrr");
      })
    }
  }

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
