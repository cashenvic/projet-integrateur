import { Component, OnInit} from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ApplicationError } from 'src/app/services/exception/application-error';
import { LoggerService } from 'src/app/services/logger/logger.service';
import { MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {
  showError = false;
  formErrorText = '';
  form: FormGroup;
  roles = [ 'Client', 'Organisateur', 'Hébergeur'];
  selected : any;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private logger: LoggerService,
    private snackBar : MatSnackBar,
    private router: Router) {
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      'nom': [null || '', Validators.required],
      'prenom': [null || '', Validators.required],
      'email': [null || '', Validators.required],
      'password': [null || '', Validators.required]
    });
  }


  /**
   * Called on sign up form validation
   */
  inscription() {
    try{
      let client = {
        nom: this.form.value.nom,
        prenom: this.form.value.prenom,
        email: this.form.value.email,
        password: this.form.value.password,
        type: this.selected
      }
      this.authService.inscription(client).then(res => {
        this.showError = false;
        this.form.reset();

        this.snackBar.open('Bienvenue, vous êtes connecté.', 'OK', {
          duration: 5000,
          panelClass: ['success-snackbar']
        });

        
        this.router.navigate(["/home"]);

      }).catch(reason => {
        this.form.value.password = '';
        this.showError = true;
        if (reason.code === "auth/email-already-in-use") {
          this.formErrorText = 'L\'email entré est déjà utilisé par un autre utilisateur';
        } else if (reason.code === "auth/too-many-requests") {
          this.formErrorText = 'Trop de tentatives infructueuses. Merci de reessayer ulterieurement';
        }else {
          this.formErrorText = 'Une erreur inconnue est survenue lors de la tentative d\'inscription';
        }
        console.log('Oops there was a problem during sign up');
        console.error(reason);
      });
    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  closeAlert() {
    this.showError = false;
  }

}
