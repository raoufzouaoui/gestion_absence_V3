import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/pages/home/home.component';
import { LoginComponent } from './components/pages/login/login.component';
import { RegisterComponent } from './components/pages/register/register.component';
import { GestionEtudiantComponent } from './components/pages/gestion-etudiant/gestion-etudiant.component';
import { AuthGuard } from './auth/guards/auth.guard';
import { AddAbsenceComponent } from './components/pages/add-absence/add-absence.component';
import { GetAllAbsenceComponent } from './components/pages/get-all-absence/get-all-absence.component';
import { HomeEtudiantComponent } from './components/pages/home-etudiant/home-etudiant.component';
import { GetJustificationByAbsenceIdComponent } from './components/pages/get-justification-by-absence-id/get-justification-by-absence-id.component';
import { ValideJustificationComponent } from './components/pages/valide-justification/valide-justification.component';

const routes: Routes = [
  {path:'',component:HomeComponent,canActivate:[AuthGuard]  },
  {path:'login',component:LoginComponent},
  {path:'register',component:RegisterComponent},
  {path:'gestion-etudiant',component:GestionEtudiantComponent,canActivate:[AuthGuard] },
  {path:'add-absence',component:AddAbsenceComponent,canActivate:[AuthGuard] },
  {path:'get-all-absence',component:GetAllAbsenceComponent,canActivate:[AuthGuard] },
  {path:'home-etudiant',component:HomeEtudiantComponent,canActivate:[AuthGuard] },
  {path:'get-justification-by-absenceId/:id',component:GetJustificationByAbsenceIdComponent,canActivate:[AuthGuard] },
  {path:'valide-justification',component:ValideJustificationComponent,canActivate:[AuthGuard] },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
