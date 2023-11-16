import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject, Observable, first, of, tap } from 'rxjs';
import { IUserLogin } from '../shared/interfaces/IUserLogin';
import { User } from '../shared/models/User';
import { GET_USER_BY_EMAIL,  ENSEIGNANT_LOGIN_URL, GET_ENSEIGNANT_BY_Email  } from '../shared/constants/urls';

const USER_KEY='User';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private token: string;
  private userSubject = new BehaviorSubject<User>(this.getUserFromLocalStorage()); //BehaviorSubject has read and write mode inside
  //it but we dont want anything outside of the user service be able to write anything inside the user subject
  // so we just need to expose the user observable that is in fact user subject that is converted into an observable so
  public userObservable:Observable<User> | undefined ; // is the read only version of the user subject

  constructor(private http:HttpClient,private toastrService:ToastrService,private router:Router) {
    this.userObservable = this.userSubject.asObservable();
  }


  public get currentUser():User{
    return this.userSubject.value;
  }

  public getUserByEmail(EmailUser: any): Observable<User> {
    return this.http.get<User>(GET_USER_BY_EMAIL+EmailUser);
  }

  public getEnseignantByEmail(enseignantEmail:string): Observable<User> {
    return this.http.get<User>(GET_ENSEIGNANT_BY_Email+enseignantEmail);
  }

  login(userLogin:IUserLogin):Observable<User>{
        return this.http.post<User>(ENSEIGNANT_LOGIN_URL,userLogin).pipe(
          tap({
            next: (user) => {
              console.log(user);
              this.token = user.access_token;
              console.log(this.token);
              this.userSubject.next(user);
               console.log(this.currentUser);
               this.setUserToLocalStorage(user);
               this.toastrService.success(
                `Welcome  ${user.firstname}`,
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

    getToken(): string {
      return this.token;
    }

    logout(){
      this.userSubject.next(new User());
      localStorage.removeItem(USER_KEY);
      this.router.navigateByUrl('login')
    }

    private setUserToLocalStorage(user:User){
        localStorage.setItem(USER_KEY, JSON.stringify(user));
    }

     getUserFromLocalStorage():User{
        const userJson = localStorage.getItem(USER_KEY);
        if(userJson) return JSON.parse(userJson) as User;
        return new User();
    }




    }
