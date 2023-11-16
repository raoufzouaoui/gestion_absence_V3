import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EtudiantService } from 'src/app/services/etudiant.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  returnUrl = '';
  loginForm!:FormGroup;
  isSubmitted= false;
  constructor(private etudiantService:EtudiantService, private formBuilder:FormBuilder,private activatedRoute:ActivatedRoute,private userService:UserService,private router:Router){

    this.loginForm = this.formBuilder.group({
          email:['',[Validators.required,Validators.email]],
          password:['',Validators.required]
        });

}

  get fc(){
    return this.loginForm.controls;
  }

  login() {
    console.log(this.loginForm.value);
    this.isSubmitted = true;
    if (this.loginForm.invalid) return;
    this.userService.getUserByEmail(this.fc.email.value).subscribe(data => {
      console.log(data);
      if (data.role === "ENSEIGNANT") {
        console.log("data");
                this.userService.login({
                  email: this.fc.email.value,
                  password: this.fc.password.value,
                }).subscribe(() => {
                  this.router.navigate(['/']); // navigate to enseignant URL
                  console.log("xxxxxxxxxxxxxxxx")
                });
              }else{
                this.etudiantService.login({
                  email: this.fc.email.value,
                  password: this.fc.password.value,
                }).subscribe(() => {
                  console.log("hhhhhhhhh")
                  this.router.navigate(['/home-etudiant']); // navigate to etudiant URL
                });
              }
    }, error => {
      alert("login failed")
      console.log(error)
    });
  }


}





