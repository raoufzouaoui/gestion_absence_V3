

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RegisterComponent } from './register.component';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { EtudiantServiceMock } from '../../mock/etudiant.service.mock'; // Import the mock service
import { ToastrServiceMock } from '../../mock/toastr.service.mock'; // Import the mock ToastrService
import { EtudiantService } from 'src/app/services/etudiant.service';
import { ToastrService } from 'ngx-toastr';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  beforeEach(async() => {
    TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: EtudiantService, useClass: EtudiantServiceMock }, // Provide the mock EtudiantService
        { provide: ToastrService, useClass: ToastrServiceMock }, // Provide the mock ToastrService
      ],
    })
    .compileComponents();



    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('Reactive Form Validation - FirstName check' ,()=> {
    let firstname = component.registerForm.controls['firstname'];
    expect(firstname.valid).toBeFalsy();
    expect(firstname.errors?.required).toBeTruthy();
  })

  it('Reactive Form Validation - set FirstName check', () => {
    let firstname = component.registerForm.controls['firstname'];
    firstname.setValue('ValidFirstName'); // Set a valid first name
    expect(firstname.valid).toBeTruthy(); // Expect it to be valid
    expect(firstname.errors?.required).toBeFalsy(); // Expect required error to be falsy
  });

  it('Reactive Form Validation - Check Invalid email address', () => {
    let email = component.registerForm.controls['email'];
    email.setValue('emailAddress'); // Set an invalid email address
    expect(email.valid).toBeFalsy(); // Expect it to be invalid
    expect(email?.errors?.email).toBeTruthy(); // Expect email validation error to be truthy
  });


  it('Reactive Form Validation - Check valid email address', () => {
    let email = component.registerForm.controls['email'];
    email.setValue('raouf@gmail.com'); // Set a valid email address
    expect(email.valid).toBeTruthy(); // Expect it to be valid
    expect(email?.errors?.email).toBeUndefined(); // Expect email validation error to be undefined
  });



})



















