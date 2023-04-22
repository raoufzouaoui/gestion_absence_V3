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

    this.userService.getEnseignantByEmail(this.fc.email.value).subscribe(data => {
      console.log(data);
      if (data) { // data is not null
        if (data.role === "enseignant") {
          console.log(data);
          this.userService.login({email: this.fc.email.value, password: this.fc.password.value}).subscribe(() => {
            this.router.navigate(['/']); // navigate to enseignant URL
          });
        }
      }else{
        this.etudiantService.login({email: this.fc.email.value, password: this.fc.password.value}).subscribe(() => {
          this.router.navigate(['/home-etudiant']);// navigate to etudiant URL
       });
      }
    }, error => {
      // handle error
      console.log("error");
      this.etudiantService.login({email: this.fc.email.value, password: this.fc.password.value}).subscribe(() => {
        this.router.navigate(['/home-etudiant']);// navigate to etudiant URL
      });
    });
  }


      // this.userService.getEnseignantByEmail(this.fc.email.value).subscribe(data => {
      //   if(data.role!="etudiant")
      //     this.router.navigate(['/']);
      //   else{
      //     this.etudiantService.login({email:this.fc.email.value,password:this.fc.password.value}).subscribe(() => {
      //         this.router.navigate(['/home-etudiant']);
      //   })
      //   }
      // })



}
