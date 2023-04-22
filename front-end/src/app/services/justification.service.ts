import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Etudiant } from '../shared/models/Etudiant';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { ADD_ETUDIANT, ADD_JUSTIFICATION, DELETE_ETUDIANT, ETUDIANT_LOGIN_URL, GET_ALL_ETUDIANT,  GET_ALL_JUSTIFICATION,  GET_ETUDIANT_BY_Email, GET_ETUDIANT_BY_GROUPE, GET_ETUDIANT_BY_ID, GET_JUSTIFICATION_BY_ID_ABSENCE, GET_JUSTIFICATION_BY_ID_ENSEIGNANT, UPDATE_ETUDIANT } from '../shared/constants/urls';
import { IUserLogin } from '../shared/interfaces/IUserLogin';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { Justification } from '../shared/models/Justification';

const ETUDIANT_KEY='User';

@Injectable({
  providedIn: 'root'
})
export class JustificationService {

  constructor(private toastrService:ToastrService,private http:HttpClient,private router:Router) {
  }

public addJustification(justification:Justification): Observable<Justification> {
  const httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
  };
  return this.http.post<Justification>(ADD_JUSTIFICATION,justification, httpOptions );
}

public getAllJustifications(): Observable<Justification[]> {
  return this.http.get<Justification[]>(GET_ALL_JUSTIFICATION);
}

public getJustificationByIdAbsence(id:number): Observable<Justification[]> {
  return this.http.get<Justification[]>(GET_JUSTIFICATION_BY_ID_ABSENCE+id);
}

public getJustificationByIdEnseignant(id:number): Observable<Justification[]> {
  return this.http.get<Justification[]>(GET_JUSTIFICATION_BY_ID_ENSEIGNANT+id);
}



}
