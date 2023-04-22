import { Component } from '@angular/core';
import { EtudiantService } from 'src/app/services/etudiant.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  constructor(private etudiantService:EtudiantService,private userService:UserService){};
  ngOnInit() {

  }
  logout(){
    this.userService.logout();
  }
}
