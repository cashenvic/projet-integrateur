import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth/auth.service';
import { MatSnackBar } from '@angular/material';
import { ApplicationError } from 'src/app/services/exception/application-error';
import { SpinnerService } from 'src/app/services/spinner/spinner/spinner.service';
import { LoggerService } from 'src/app/services/logger/logger.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  showError = false;
  formErrorText = '';
  form: FormGroup;
  roles = [ 'Client', 'Organisateur', 'Hébergeur'];
  selected ;

  constructor(private router : Router,
    private authService : AuthService,
    private formBuilder: FormBuilder,
    private snackBar : MatSnackBar,
    private spinner: SpinnerService,
    private logger :LoggerService,
    ) {
  }

   ngOnInit() {
    this.form = this.formBuilder.group({
      'email': [null || '', Validators.required],
      'pwd': [null || '', Validators.required]
    });

  }

  submit() {
    if (this.form.valid && this.selected) {
      this.showError = false;
      this.loginMail();
    }
    else{
      this.showError = true;
      this.formErrorText = 'Veuillez remplir tout les champs et selectionnez le type de connexion';
    }
  }

  /**
   * Called when login form is validated
   */
  async loginMail() {
    try{
      this.spinner.show();
      await this.authService.loginMail(this.form.value.email, this.form.value.pwd, this.selected).then((res) => {
        //successful logging -> close the modal -> notify
        this.showError = false;
        this.form.value.email = '';
        this.form.value.pwd = '';
        this.router.navigate(["/panier"]);

      }).catch((reason => {
        this.form.value.pwd = '';
        this.showError = true;
        if (reason.code === "auth/wrong-pwd") {
          this.formErrorText = 'Le mot de passe saisi est invalide';
        } else if (reason.code === "auth/user-not-found") {
          this.formErrorText = 'L\'utilisateur saisi est introuvable';
        } else if (reason.code === "auth/too-many-requests") {
          this.formErrorText = 'Trop de tentatives infructueuses. Merci de reessayer ulterieurement';
          //disable inputs ???
        }else {
          this.formErrorText = 'Une erreur inconnue est survenue lors de la tentative de connexion';
        }
        console.error(reason);
      }));
    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    } finally {
      this.spinner.hide();

      this.snackBar.open('Bienvenue, vous êtes connecté.', 'OK', {
        duration: 10000,
        panelClass: ['success-snackbar']
      });

    }
  }


  /**
   * Called when login form is validated
   */
  async loginFB() {
    try{
      this.spinner.show();
      await this.authService.facebookAuth(this.selected).then((res) => {
        //successful logging -> close the modal -> notify
        this.showError = false;
        this.form.value.email = '';
        this.form.value.pwd = '';

        this.snackBar.open('Bienvenue, vous êtes connecté.', 'OK', {
          duration: 5000,
          panelClass: ['success-snackbar']
        });

        this.router.navigate(["/panier"]);
      }).catch((reason => {
        this.showError = false;
        this.form.value.email = '';
        this.form.value.pwd = '';
        console.error('Oops an error occurred while logging in');
        console.error(reason);
      }));
    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    } finally {
      this.spinner.hide();
    }
  }

  closeAlert() {
    this.showError = false;
  }

}
