import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Etudiant } from '../shared/models/Etudiant';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import {  ETUDIANT_LOGIN_URL, ETUDIANT_REGISTER_URL, GET_ALL_ETUDIANT,  GET_ETUDIANT_BY_Email, GET_ETUDIANT_BY_GROUPE, GET_ETUDIANT_BY_ID  } from '../shared/constants/urls';
import { IUserLogin } from '../shared/interfaces/IUserLogin';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { IUserRegister } from '../shared/interfaces/IUserRegister';
import { UserService } from './user.service';

const ETUDIANT_KEY='User';

@Injectable({
  providedIn: 'root'
})
export class EtudiantService {

   private EtudiantSubject = new BehaviorSubject<Etudiant>(this.getEtudiantFromLocalStorage());
   public EtudiantObservable:Observable<Etudiant> | undefined ;
   EtudiantsSelected : Etudiant = new Etudiant();

   constructor(private userService: UserService,private toastrService:ToastrService,private http:HttpClient,private router:Router) {
     this.EtudiantObservable = this.EtudiantSubject.asObservable();
   }

   public get currentUser():Etudiant{
    return this.EtudiantSubject.value;
  }

  register(userRegiser:IUserRegister): Observable<Etudiant>{
    console.log(userRegiser);
    return this.http.post<Etudiant>(ETUDIANT_REGISTER_URL, userRegiser).pipe(
      tap({
        next: (user) => {
          console.log(user);
          this.setEtudiantToLocalStorage(user);
          this.EtudiantSubject.next(user);
          this.toastrService.success(
            `Welcome ${user.firstname}`,
            'Register Successful'
          )
        },
        error: (errorResponse) => {
          this.toastrService.error(errorResponse.error,
            'Register Failed')
        }
      })
    )
  }

   login(etudiantLogin:IUserLogin):Observable<Etudiant>{
    return this.http.post<Etudiant>(ETUDIANT_LOGIN_URL,etudiantLogin).pipe(
      tap({
        next: (etudiant: Etudiant) => {
          this.setEtudiantToLocalStorage(etudiant);
          console.log(etudiant);
          if (etudiant !== null) {
            this.EtudiantSubject.next(etudiant);
            const message = `Welcome ${etudiant.firstname}, 'Login Successful'`;
            document.getElementById('login-message')!.innerHTML = message;
            this.router.navigate(['/home-etudiant']);
            // console.log(this.currentUser)
          }
        },
        error: (errorResponse: { error: string | undefined; }) => {
          const message = `'Login Failed'`;
            document.getElementById('login-message')!.innerHTML = message;
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

  public getEtudiantById(etudiantId:number): Observable<Etudiant> {
    return this.http.get<Etudiant>(GET_ETUDIANT_BY_ID+etudiantId);
  }

  public getEtudiantByEmail(etudiantEmail:string): Observable<Etudiant> {
    return this.http.get<Etudiant>(GET_ETUDIANT_BY_Email+etudiantEmail);
  }

  public getEtudiantByGroupe(etudiantGroupe:string): Observable<Etudiant[]> {
    return this.http.get<Etudiant[]>(GET_ETUDIANT_BY_GROUPE+etudiantGroupe);
  }

  // public addEtudiant(etudiant:Etudiant): Observable<Etudiant> {
  //   const httpOptions = {
  //     headers: new HttpHeaders({
  //       'Content-Type':  'application/json'
  //     })
  //   };
  //   return this.http.post<Etudiant>(ADD_ETUDIANT,etudiant, httpOptions );
  // }

  // public updateEtudiant(etudiant:Etudiant): Observable<Etudiant> {
  //   return this.http.put<Etudiant>(UPDATE_ETUDIANT,etudiant);
  // }

  // public deleteEtudiant(etudiantId: number): Observable<void> {
  //   return this.http.delete<void>(DELETE_ETUDIANT+etudiantId);
  // }

  private setEtudiantToLocalStorage(etudiant:Etudiant){
    localStorage.setItem(ETUDIANT_KEY, JSON.stringify(etudiant));
}

private getEtudiantFromLocalStorage():Etudiant{
    const etudiantJson = localStorage.getItem(ETUDIANT_KEY);
    if(etudiantJson) return JSON.parse(etudiantJson) as Etudiant;
    return new Etudiant();
}

getSomeData() {
  const headers = new HttpHeaders({
    'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYW91ZkBnbWFpbC5jb20iLCJpYXQiOjE2OTkzNjE2NzksImV4cCI6MTY5OTQ0ODA3OX0.We1bYKGLV_2vRN0akUWJw-bHB7cttC8kw05BwsvZxmo'
  });

  const options = { headers: headers };

  // Replace the URL with your API endpoint
  this.http.get('http://localhost:8080/api/v1/enseignant/getAllEtudiant', options)
    .subscribe(
      (response) => {
        // Handle the successful response
        console.log('Response:', response);
      },
      (error) => {
        // Handle any errors here
        console.error('Error:', error);
      }
    );
}


}
