import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { switchMap } from 'rxjs/operators';
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
    const email = this.userService.currentUser.email;
    this.userService.getEnseignantByEmail(email)
    .pipe(
      switchMap(enseignantData => {
        console.log(enseignantData.id)
        return this.justificationService.getJustificationByIdEnseignant(enseignantData.id);
      })
    )
    .subscribe(
      justificationsData => {
        this.justifications = justificationsData;
        console.log(justificationsData)
      },
      error => {
        console.error("An error occurred while fetching justifications:", error);
      }
    );
  }


  public onUpdateAbsence(absence: Absence): void {
    console.log(absence.id);
    console.log(absence);
    this.absenceService.updateAbsence(absence).subscribe(
      data => {
        console.log(absence);
        const email = this.userService.currentUser.email;
        this.userService.getEnseignantByEmail(email)
        .pipe(
          switchMap(enseignantData => {
            console.log(enseignantData.id)
            return this.justificationService.getJustificationByIdEnseignant(enseignantData.id);
          })
        )
        .subscribe(
          justificationsData => {
            this.justifications = justificationsData;
            console.log(justificationsData)
          },
          error => {
            console.error("An error occurred while fetching justifications:", error);
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
