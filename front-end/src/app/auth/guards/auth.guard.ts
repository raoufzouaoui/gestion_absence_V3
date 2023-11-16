import { Injectable, Inject } from '@angular/core';
import { UserService } from '../../services/user.service';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { EtudiantService } from '../../services/etudiant.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private userService:UserService, private router:Router,private etudiantService:EtudiantService){

  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if(this.userService.currentUser.email || this.etudiantService.currentUser.email){
      return true;
    }
      this.router.navigate(['login']);
    return false;
  }

}































