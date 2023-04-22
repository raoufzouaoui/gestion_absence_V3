import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ADD_ABSENCE, ADD_ETUDIANT, DELETE_ETUDIANT, GET_ABSENCE_BY_ID, GET_ABSENCE_BY_ID_Etudiant, GET_ALL_ABSENCE, GET_MATIERE_BY_ID, GET_MATIERE_BY_NOM, UPDATE_ABSENCE, UPDATE_ETUDIANT } from '../shared/constants/urls';
import { Absence } from '../shared/models/Absence';
import { Matiere } from '../shared/models/Matiere';

const ETUDIANT_KEY='User';

@Injectable({
  providedIn: 'root'
})
export class AbsenceService {

  constructor(private http:HttpClient) {
  }

  public addAbsence(absence:Absence[]): Observable<Absence[]> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
    return this.http.post<Absence[]>(ADD_ABSENCE,absence, httpOptions );
  }


  public getAllAbsence(): Observable<Absence[]> {
    return this.http.get<Absence[]>(GET_ALL_ABSENCE);
  }

  public getAbsenceById(id:number): Observable<Absence> {
    return this.http.get<Absence>(GET_ABSENCE_BY_ID+id);
  }

  public getAbsencesByIdEtudiant(id:number): Observable<Absence[]> {
    return this.http.get<Absence[]>(GET_ABSENCE_BY_ID_Etudiant+id);
  }

  public getMatiereById(id:number): Observable<Matiere> {
    return this.http.get<Matiere>(GET_MATIERE_BY_ID+id);
  }

  public getMatiereByNom(nom:string): Observable<Matiere> {
    return this.http.get<Matiere>(GET_MATIERE_BY_NOM+nom);
  }

  public updateAbsence(absence: Absence): Observable<Absence> {
    return this.http.put<Absence>(UPDATE_ABSENCE,absence);
  }


}
