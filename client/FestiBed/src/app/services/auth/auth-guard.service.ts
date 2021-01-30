import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import * as firebase from 'firebase/app';
import { CanActivate, Router, NavigationEnd } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { AuthService } from './auth.service';
import { Location } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {
  route;
  constructor(public snackBar: MatSnackBar, private router: Router,
    private authService : AuthService, private location : Location) { 
      this.router.events.subscribe(val => {
        if (this.location.path() != '') {
          this.route = this.location.path();
        } else {
          if (val instanceof NavigationEnd) {
            this.route = val.url;
          }
        }
      });
    }

  //une guard de router permettant de restreinte les accès au page si on n'est pas connecté
  canActivate(): Observable<boolean> | Promise<boolean> | boolean {
    return new Promise(
      (resolve, reject) => {
        firebase.auth().onAuthStateChanged(
          (user) => {
            if (user) {
              const utilisateur = this.authService.user;

              if(utilisateur.type === "Client" && (this.route == "gestion-festival" || this.route == "gestion-hebergement") ){
                this.router.navigate(['home']);
                this.snackBar.open('Accès refusé', 'OK', {
                  duration: 2000,
                  panelClass: ['error-snackbar']
                });
                reject(false);
              }
              else if(( utilisateur.type === "Organisateur"  || utilisateur.type === "Hébergeur") && 
              (this.route == "festivals" || this.route == "hebergements" || this.route == "festival" || this.route == "hebergement") ){
                this.router.navigate(['home']);
                this.snackBar.open('Accès refusé', 'OK', {
                  duration: 2000,
                  panelClass: ['error-snackbar']
                });
                reject(false);
              }
              else if( this.route == "signup" || this.route == "login" ){
                this.router.navigate(['home']);
                this.snackBar.open('Accès refusé', 'OK', {
                  duration: 2000,
                  panelClass: ['error-snackbar']
                });
                reject(false);
              }
              else{
                resolve(true);
              }

            } else {
              if(this.route == "gestion-festival" || this.route == "gestion-hebergement"){
                this.router.navigate(['home']);
                this.snackBar.open('Accès refusé', 'OK', {
                  duration: 2000,
                  panelClass: ['error-snackbar']
                });
                reject(false);
              }
              else{
                resolve(true);
              }
             
            }
          }
        );
      }
    );
  }

}


