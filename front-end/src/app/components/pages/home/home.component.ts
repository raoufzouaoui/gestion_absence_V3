import { Component } from '@angular/core';
import { EtudiantService } from 'src/app/services/etudiant.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
constructor(private etudiantService:EtudiantService,private userService:UserService){};
ngOnInit() {

}
logout(){
  this.userService.logout();
}
}
