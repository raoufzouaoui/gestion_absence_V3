import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AbsenceService } from 'src/app/services/absence.service';
import { EtudiantService } from 'src/app/services/etudiant.service';
import { JustificationService } from 'src/app/services/justification.service';
import { Justification } from 'src/app/shared/models/Justification';

@Component({
  selector: 'app-get-justification-by-absence-id',
  templateUrl: './get-justification-by-absence-id.component.html',
  styleUrls: ['./get-justification-by-absence-id.component.css']
})
export class GetJustificationByAbsenceIdComponent {
  public justifications:Justification[];
  constructor(private activatedroute:ActivatedRoute,private justificationService:JustificationService,
     private etudiantService:EtudiantService){ }

  logout(){
    this.etudiantService.logout();
  }

  ngOnInit() {
    this.activatedroute.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.justificationService.getJustificationByIdAbsence(id).subscribe(
          data => this.justifications = data,
          error => console.error(error)
        );
      }
    });
  }

  public searchEtudiants(key: string): void {
    console.log(key);
    const results: Justification[] = [];
    if(this.justifications)
      for (const justification of this.justifications) {
        if ( justification.absence.id.toString().toLowerCase().indexOf(key.toLowerCase()) !== -1 ) {
          results.push(justification);
        }
      }
    this.justifications = results;
    if (results.length === 0 || !key) {
      this.activatedroute.params.subscribe(params => {
      this.justificationService.getJustificationByIdAbsence(params.id).subscribe(
        data => this.justifications = data,
        error => console.error(error)
      );
  });
  }
 }

}
