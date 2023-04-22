import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { AbsenceService } from 'src/app/services/absence.service';
import { JustificationService } from 'src/app/services/justification.service';
import { UserService } from 'src/app/services/user.service';
import { Absence } from 'src/app/shared/models/Absence';
import { Justification } from 'src/app/shared/models/Justification';

@Component({
  selector: 'app-valide-justification',
  templateUrl: './valide-justification.component.html',
  styleUrls: ['./valide-justification.component.css']
})
export class ValideJustificationComponent {

  public justifications : Justification[];
  public editAbsence: Absence | undefined;

  constructor(private userService:UserService,
    private absenceService:AbsenceService,
    private justificationService:JustificationService) {}

  ngOnInit() {
    const id = this.userService.currentUser.id;
    this.justificationService.getJustificationByIdEnseignant(id).subscribe(data => {
      this.justifications = data;
    })
  }


  public onUpdateAbsence(absence: Absence): void {
    console.log(absence.id);
    console.log(absence);
    this.absenceService.updateAbsence(absence).subscribe(
      data => {
        console.log(absence);
        const id = this.userService.currentUser.id;
        this.justificationService.getJustificationByIdEnseignant(id).subscribe(
          data => {
            this.justifications = data;
          },
          error => {
            console.log("errorrrrrrrrrrrr");
          }
        );
      },
      error => {
        alert(error.message);
      }
    );
  }



  public onOpenModal(justification: Justification | any, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'edit') {
      this.editAbsence = justification.absence;
      button.setAttribute('data-target', '#updateAbsenceModal');
    }
    if(container)
      container.appendChild(button);
    button.click();
  }



}
