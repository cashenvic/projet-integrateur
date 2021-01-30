import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ListeFestivalsComponent } from './components/liste-festivals/liste-festivals.component';
import { ListeBedComponent } from './components/liste-bed/liste-bed.component';
import { ProfilComponent } from './components/profil/profil.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import {SearchComponent} from './components/search/search.component';
import {SearchBedComponent} from './components/search-bed/search-bed.component';
import {FestivalComponent} from './components/festival/festival.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { PanierComponent } from './components/panier/panier/panier.component';
import { GestionFestivalComponent } from './components/gestion-festival/gestion-festival.component';
import { GestionLogementComponent } from './components/gestion-logement/gestion-logement.component';
import { AuthGuardService } from './services/auth/auth-guard.service';
import { BedComponent } from './components/bed/bed.component';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'hebergements',
    component: ListeBedComponent
  },
  {
    path: 'festivals',
    component: ListeFestivalsComponent
  },
  {
    path: 'profil',
    canActivate: [AuthGuardService],
    component: ProfilComponent,
  },
  {
    path: 'search',
    component: SearchComponent
  },
  {
    path: 'searchbed',
    component: SearchBedComponent
  },
  {
    path: 'festival/:id',
    component: FestivalComponent
  },
  {
    path: 'hebergement/:id',
    component: BedComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'signup',
    component: SignupComponent
  },
  {
    path: 'panier',
    component: PanierComponent
  },
  {
    path: 'gestion-festival',
    canActivate: [AuthGuardService],
    component: GestionFestivalComponent
  },
  {
    path: 'gestion-hebergement',
    canActivate: [AuthGuardService],
    component: GestionLogementComponent
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
