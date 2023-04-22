import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/shared/models/User';
import { UserService } from '../../../services/user.service';
import { IUserRegister } from 'src/app/shared/interfaces/IUserRegister';
import { PasswordsMatchValidator } from 'src/app/shared/validators/password_match_validators';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm!:FormGroup;
  isSubmitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      nom: ['', [Validators.required, Validators.minLength(5)]],
      prenom: ['', [Validators.required, Validators.minLength(5)]],
      email: ['', [Validators.required, Validators.email]],
      numero: ['', [Validators.required, Validators.minLength(5)]],
      password: ['', [Validators.required, Validators.minLength(5)]],
      confirmPassword: ['', Validators.required],
    },{
      validators: PasswordsMatchValidator('password','confirmPassword')
    });

  }

  get fc() {
    return this.registerForm.controls;
  }

  register(){
    //  console.log(this.registerForm.value);
    this.isSubmitted = true;
    if(this.registerForm.invalid) return;

    const fv= this.registerForm.value;
    const user :IUserRegister = {
      nom: fv.nom,
      prenom: fv.prenom,
      email: fv.email,
      numero: fv.numero,
      password: fv.password,
      confirmPassword: fv.confirmPassword,
    };
    // console.log(user);
  this.userService.register(user).subscribe( data => {
    // console.log(data);
      alert("register successfully")
      this.router.navigateByUrl("/login");
    },error =>{
      alert("register failed")
    })
  }

}

