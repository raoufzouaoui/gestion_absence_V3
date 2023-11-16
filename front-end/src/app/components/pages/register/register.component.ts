import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/shared/models/User';
import { UserService } from '../../../services/user.service';
import { IUserRegister } from 'src/app/shared/interfaces/IUserRegister';
import { PasswordsMatchValidator } from 'src/app/shared/validators/password_match_validators';
import { EtudiantService } from 'src/app/services/etudiant.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  
    Groupes: string[] = [
    'info1A', 'info1B', 'info1C', 'info1D',
    'info2A', 'info2B', 'info2C', 'info2D',
    'info3A', 'info3B', 'info3C', 'info3D'
  ];
  registerForm!:FormGroup;
  isSubmitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private etudiantService: EtudiantService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      cin: ['', [Validators.required, Validators.minLength(8)]],
      firstname: ['', [Validators.required, Validators.minLength(4)]],
      lastname: ['', [Validators.required, Validators.minLength(4)]],
      email: ['', [Validators.required, Validators.email]],
      dateNaissance: ['', [Validators.required]],
      groupe:['', [Validators.required, Validators.minLength(4)]],
      numero: ['', [Validators.required, Validators.minLength(5)]],
      password: ['', [Validators.required, Validators.minLength(5)]],
      confirmPassword: ['', Validators.required],
      role: ['ETUDIANT', Validators.required],
    },{
      validators: PasswordsMatchValidator('password', 'confirmPassword')
    });

  }

  get fc() {
    return this.registerForm.controls;
  }

  register(){
    console.log(this.registerForm.value);
    this.isSubmitted = true;
    const fv= this.registerForm.value;
    fv.role = "ETUDIANT";
    if(this.registerForm.invalid)
    {console.log("registerForm.invalid");
    for (const controlName in this.registerForm.controls) {
      const control = this.registerForm.get(controlName);
      if (control && control.invalid) {
        console.log(`Validation error for ${controlName}:`, control.errors);
      }
    }
      return;
    }
    const dateObject = new Date(fv.dateNaissance);
    const formattedDate = dateObject.toISOString().slice(0, 19).replace("T", " ");
    // Now, "formattedDate" contains the date in the desired format
    console.log(formattedDate);
    const user :IUserRegister = {
      cin: fv.cin,
      firstname: fv.firstname,
      lastname: fv.lastname,
      email: fv.email,
      dateNaissance: formattedDate,
      groupe: fv.groupe,
      numero: fv.numero,
      password: fv.password,
      confirmPassword: fv.confirmPassword,
      role:fv.role
    };
     console.log(user);
  this.etudiantService.register(user).subscribe( data => {
    console.log(data);
      alert("register successfully")
      this.router.navigateByUrl("/login");
    },error =>{
      alert("register failed")
    })
  }

}

