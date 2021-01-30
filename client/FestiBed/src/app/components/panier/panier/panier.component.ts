import { Component, OnInit, ViewChild } from '@angular/core';
import { ApplicationError } from 'src/app/services/exception/application-error';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { BreakpointState, BreakpointObserver } from '@angular/cdk/layout';
import { MatSnackBar, MatVerticalStepper } from '@angular/material';
import { LoggerService } from 'src/app/services/logger/logger.service';
import { Router } from '@angular/router';
import { CommandeService } from 'src/app/services/commande/commande.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { UtilisateurInterface } from 'src/app/interfaces/utilisateur';
import { Location } from '@angular/common';
import { SpinnerService } from 'src/app/services/spinner/spinner/spinner.service';

@Component({
  selector: 'app-panier',
  templateUrl: './panier.component.html',
  styleUrls: ['./panier.component.scss']
})
export class PanierComponent implements OnInit {
  @ViewChild('stepper', {static: false}) stepper: MatVerticalStepper;
  step = 1;
  isStateMobile = false;

  user: UtilisateurInterface ;

  constructor(
              private route: Router,
              private breakpointObserver: BreakpointObserver,
              private commande: CommandeService,
              private logger: LoggerService,
              private authService: AuthService,
              private snackBar: MatSnackBar,
              private location : Location,
              private spinner : SpinnerService
  ) {
    this.breakpointObserver
      .observe(['(max-width: 768px)'])
      .subscribe((state: BreakpointState) => {
        this.isStateMobile = state.matches;
      });
  }

  ngOnInit() {
  }

  /**
   * finalisation de la commande avec post vers le server qui renvoie un get facture
   */
  public async finaliserCommande($event) {
    try {
      this.spinner.show();
      console.log("finaliser");
      this.step = 3;

    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }finally{
      this.spinner.hide();
    }
  }

  /**
   * sauvegarde les données passés en parametre et passe au step 2
   * @param $event
   */
  toStep2($event) {
    try {
      if (this.authService.isAuth) {
        if (!this.isStateMobile) {
          this.stepper.selectedIndex = 1;
        }
        this.step = 2;
      } else {
        this.snackBar.open("Pour valider la commande, il faut vous connecter.", 'OK', {
          duration: 15000,
          panelClass: ['info-snackbar']
        });
      }
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }


  /**
   * stepper back
   */
  previousStepper() {
    this.stepper.previous();
    if (!this.isStateMobile) {
      this.step = this.stepper.selectedIndex + 1;
    }
  }
}
