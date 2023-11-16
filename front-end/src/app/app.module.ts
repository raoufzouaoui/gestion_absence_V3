import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './components/pages/register/register.component';
import { LoginComponent } from './components/pages/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './components/pages/home/home.component';
import { NavbarComponent } from './components/partials/navbar/navbar.component';
import { GestionEtudiantComponent } from './components/pages/gestion-etudiant/gestion-etudiant.component';
import { AddAbsenceComponent } from './components/pages/add-absence/add-absence.component';
import { GetAllAbsenceComponent } from './components/pages/get-all-absence/get-all-absence.component';
import { HomeEtudiantComponent } from './components/pages/home-etudiant/home-etudiant.component';
import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import { GetJustificationByAbsenceIdComponent } from './components/pages/get-justification-by-absence-id/get-justification-by-absence-id.component';
import { ValideJustificationComponent } from './components/pages/valide-justification/valide-justification.component';
import {  HttpInterceptorService } from './auth/auth.interceptor';

registerLocaleData(localeFr, 'fr-TN');

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    HomeComponent,
    NavbarComponent,
    GestionEtudiantComponent,
    AddAbsenceComponent,
    GetAllAbsenceComponent,
    HomeEtudiantComponent,
    GetJustificationByAbsenceIdComponent,
    ValideJustificationComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      timeOut:3000,
      positionClass:'toast-bottom-right',
      newestOnTop:false
   }) ,
  ],
  providers: [{ provide: LOCALE_ID, useValue: 'fr-TN' }, {
    provide: HTTP_INTERCEPTORS,
    useClass: HttpInterceptorService,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
