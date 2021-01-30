import { Component, OnInit } from '@angular/core';
import { CookieInterface } from 'src/app/interfaces/cookie';
import { CommandeService } from 'src/app/services/commande/commande.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Route, Router } from '@angular/router';
import { ApplicationError } from 'src/app/services/exception/application-error';
import { LoggerService } from 'src/app/services/logger/logger.service';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})


export class HeaderComponent implements OnInit {
  /**
   * item de navigation selectioné
   */
  selected;

  cookie : CookieInterface = {
    accept : false,
    description : "Nous utilisons les cookies pour améliorer FestiBed. En continuant, vous acceptez notre politique en matière de cookies."
  };

  get getCookie() {
    let existing = localStorage.getItem('Cookie') || null;
    const cookie = JSON.parse(existing);

    if(cookie == null){
      this.cookie = {
        accept : false,
        description : "Nous utilisons les cookies pour améliorer FestiBed. En continuant, vous acceptez notre politique en matière de cookies."
      }

      localStorage.setItem('Cookie', JSON.stringify(this.cookie));

    }else{
      this.cookie = cookie;
    }

    return this.cookie || [];
  }

  constructor(private commande : CommandeService, 
    private authService : AuthService,
    private router : Router,
    private logger :LoggerService,
    private snackBar : MatSnackBar) { 
  }

  ngOnInit() {
  }

  /**
   * Réagit à la modification de changement de navigation.
   * @param item : Item de menu courant.
   */
  changeSelected(item: number) {
    try {
      this.selected = item;
      switch(item){
        case 0 :
          this.router.navigate(['/festivals']);
          break;
        case 1 :
          this.router.navigate(['/gestion-festival']);
          break;
        case 2 :
          this.router.navigate(['/hebergements']);
          break;
        case 3 :
          this.router.navigate(['/gestion-hebergement']);
          break;
        case 4 :
          this.router.navigate(['/login']);
          break;
        case 5 :
          this.router.navigate(['/signup']);
          break;
        case 6 :
          this.router.navigate(['/panier']);
          break;
      }

    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  accept(){
    try{
      this.cookie.accept = true;
      localStorage.setItem('Cookie', JSON.stringify(this.cookie));
    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  logout(){
    try{
      this.authService.doLogout().then(res =>{
        this.snackBar.open('Bienvenue, vous êtes connecté.', 'OK', {
          duration: 5000,
          panelClass: ['success-snackbar']
        });

        this.router.navigate(["/home"]);
      });
    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }
}
