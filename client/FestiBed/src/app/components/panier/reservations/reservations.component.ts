import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';
import { LoggerService } from 'src/app/services/logger/logger.service';
import { ApplicationError } from 'src/app/services/exception/application-error';
import { SpinnerService } from 'src/app/services/spinner/spinner/spinner.service';
import { CommandeService } from 'src/app/services/commande/commande.service';
import { MatDialog } from '@angular/material';
import { DialogAddComponent } from '../../dialog-add/dialog-add.component';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.scss', '../panier-commande.component.scss']
})
export class ReservationsComponent implements OnInit {
  @Output() nextStep2 = new EventEmitter();
  @Input() isStateMobile: boolean = false;

  showError = false;
  formErrorText = [];

  constructor(private commande : CommandeService,
    private logger: LoggerService,
    private spinner : SpinnerService,
    private dialog : MatDialog) { 
  }

  ngOnInit() {      
  }

  /**
   * aller sur le stepper infp
   */
  next() {
    try{
      let reponse = this.commande.verifPanier();
      this.formErrorText = [];
      if(reponse === 0) {
       this.nextStep2.emit();
      }
      else if(reponse === 1 ){
        this.formErrorText.push("Pour valider la commande, il faut que les réservations de logements corespondent aux festivals en terme de nombre de personnes.");
        this.showError = true;
      }else if(reponse === 2 ){
        this.formErrorText.push("Pour valider la commande, il faut que les dates de réservations de festivals soient compris aux dates de réservations des logements.");
        this.showError = true;
      }else {
        this.formErrorText.push("Pour valider la commande, il faut que le panier contient au moins une réservation d'un logement et une réservation de festivals");
        this.showError = true;
      }

    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  /**
   * supprimer un festival
   * @param index 
   */
  removeFestival(index: any){
    try{
      this.commande.removeFestival(index);

    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  /**
   * supprimer un hebergement
   * @param index 
   */
  removeHebergement(index: any){
    try{
      this.commande.removeFestival(index);
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }


  closeAlert() {
    this.showError = false;
  }
}