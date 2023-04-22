import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Etudiant } from '../shared/models/Etudiant';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { ADD_ETUDIANT, ADD_JUSTIFICATION, DELETE_ETUDIANT, ETUDIANT_LOGIN_URL, GET_ALL_ETUDIANT,  GET_ETUDIANT_BY_Email, GET_ETUDIANT_BY_GROUPE, GET_ETUDIANT_BY_ID, UPDATE_ETUDIANT } from '../shared/constants/urls';
import { IUserLogin } from '../shared/interfaces/IUserLogin';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { Justification } from '../shared/models/Justification';

const ETUDIANT_KEY='User';

@Injectable({
  providedIn: 'root'
})
export class EtudiantService {

   private EtudiantSubject = new BehaviorSubject<Etudiant>(this.getEtudiantFromLocalStorage());
   public EtudiantObservable:Observable<Etudiant> | undefined ;
   EtudiantsSelected : Etudiant = new Etudiant();

   constructor(private toastrService:ToastrService,private http:HttpClient,private router:Router) {
     this.EtudiantObservable = this.EtudiantSubject.asObservable();
   }

   public get currentUser():Etudiant{
    return this.EtudiantSubject.value;
  }

   login(etudiantLogin:IUserLogin):Observable<Etudiant>{
    return this.http.post<Etudiant>(ETUDIANT_LOGIN_URL,etudiantLogin).pipe(
      tap({
        next: (etudiant: Etudiant) => {
           this.setEtudiantToLocalStorage(etudiant);
           this.EtudiantSubject.next(etudiant);
           this.toastrService.success(
            `Welcome  ${etudiant.nom}`,
            'Login Successful'
          )
          this.router.navigate(['/home-etudiant']);
        },
        error: (errorResponse: { error: string | undefined; }) => {
          this.toastrService.error(errorResponse.error,
            'Login Failed')
        }
      })
    )
}

logout(){
  this.EtudiantSubject.next(new Etudiant());
  localStorage.removeItem(ETUDIANT_KEY);
  this.router.navigateByUrl('login')
}

  public getAllEtudiants(): Observable<Etudiant[]> {
    return this.http.get<Etudiant[]>(GET_ALL_ETUDIANT);
  }

  public getEtudiantById(etudiantId:Etudiant): Observable<Etudiant> {
    return this.http.get<Etudiant>(GET_ETUDIANT_BY_ID+etudiantId);
  }

  public getEtudiantByEmail(etudiantEmail:Etudiant): Observable<Etudiant> {
    return this.http.get<Etudiant>(GET_ETUDIANT_BY_Email+etudiantEmail);
  }

  public getEtudiantByGroupe(etudiantGroupe:Etudiant[]): Observable<Etudiant[]> {
    return this.http.get<Etudiant[]>(GET_ETUDIANT_BY_GROUPE+etudiantGroupe);
  }

  public addEtudiant(etudiant:Etudiant): Observable<Etudiant> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
    return this.http.post<Etudiant>(ADD_ETUDIANT,etudiant, httpOptions );
  }

  public updateEtudiant(etudiant:Etudiant): Observable<Etudiant> {
    return this.http.put<Etudiant>(UPDATE_ETUDIANT,etudiant);
  }

  public deleteEtudiant(etudiantId: number): Observable<void> {
    return this.http.delete<void>(DELETE_ETUDIANT+etudiantId);
  }

  private setEtudiantToLocalStorage(etudiant:Etudiant){
    localStorage.setItem(ETUDIANT_KEY, JSON.stringify(etudiant));
}

private getEtudiantFromLocalStorage():Etudiant{
    const etudiantJson = localStorage.getItem(ETUDIANT_KEY);
    if(etudiantJson) return JSON.parse(etudiantJson) as Etudiant;
    return new Etudiant();
}




}
