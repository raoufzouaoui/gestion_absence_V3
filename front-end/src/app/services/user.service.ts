import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject, Observable, tap } from 'rxjs';
// import { USER_LOGIN_URL, USER_REGISTER_URL } from '../shared/constants/urls';
import { IUserLogin } from '../shared/interfaces/IUserLogin';
import { IUserRegister } from '../shared/interfaces/IUserRegister';
import { User } from '../shared/models/User';
import { GET_ENSEIGNANT_BY_EMAIL,  USER_LOGIN_URL, USER_REGISTER_URL } from '../shared/constants/urls';

const USER_KEY='User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userSubject = new BehaviorSubject<User>(this.getUserFromLocalStorage()); //BehaviorSubject has read and write mode inside it but we dont want anything outside of the user service be able to write anything inside the user subject
  // so we just need to expose the user observable that is in fact user subject that is converted into an observable so
  public userObservable:Observable<User> | undefined ; // is the read only version of the user subject
  constructor(private http:HttpClient,private toastrService:ToastrService,private router:Router) {
    this.userObservable = this.userSubject.asObservable();
  }

  public get currentUser():User{
    return this.userSubject.value;
  }

  public getEnseignantByEmail(EnseignantEmailUser: any): Observable<User> {
    return this.http.get<User>(GET_ENSEIGNANT_BY_EMAIL+EnseignantEmailUser);
  }

  login(userLogin:IUserLogin):Observable<User>{
        return this.http.post<User>(USER_LOGIN_URL,userLogin).pipe(
          tap({
            next: (user) => {
               this.setUserToLocalStorage(user);
               this.userSubject.next(user);
               this.toastrService.success(
                `Welcome  ${user.nom}`,
                'Login Successful'
              )
            },
            error: (errorResponse) => {
              this.toastrService.error(errorResponse.error,
                'Login Failed')
            }
          })
        )
    }

    register(userRegiser:IUserRegister): Observable<User>{
      console.log(userRegiser);
      return this.http.post<User>(USER_REGISTER_URL, userRegiser).pipe(
        tap({
          next: (user) => {
            this.setUserToLocalStorage(user);
            this.userSubject.next(user);
            this.toastrService.success(
              `Welcome ${user.nom}`,
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

    logout(){
      this.userSubject.next(new User());
      localStorage.removeItem(USER_KEY);
      this.router.navigateByUrl('login')
    }

    private setUserToLocalStorage(user:User){
        localStorage.setItem(USER_KEY, JSON.stringify(user));
    }

    private getUserFromLocalStorage():User{
        const userJson = localStorage.getItem(USER_KEY);
        if(userJson) return JSON.parse(userJson) as User;
        return new User();
    }




    }
