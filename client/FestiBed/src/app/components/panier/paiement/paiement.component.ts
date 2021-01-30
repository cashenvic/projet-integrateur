import { Component, OnInit, Input, EventEmitter, Output, ViewChild, ElementRef } from '@angular/core';
import { ApplicationError } from 'src/app/services/exception/application-error';
import { LoggerService } from 'src/app/services/logger/logger.service';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-paiement',
  templateUrl: './paiement.component.html',
  styleUrls: ['./paiement.component.scss', '../panier-commande.component.scss']
})
export class PaiementComponent implements OnInit {
  @ViewChild('numerolField', {read: ElementRef, static: false}) emailField: ElementRef;
  @ViewChild('nomlField', {read: ElementRef, static: false}) nomlField: ElementRef;
  @ViewChild('prenomField', {read: ElementRef, static: false}) prenomField: ElementRef;
  @ViewChild('expiryField', {read: ElementRef, static: false}) expiryField: ElementRef;
  @ViewChild('cvcField', {read: ElementRef, static: false}) cvcField: ElementRef;

  @Output() nextStep3 : EventEmitter<FormGroup> = new EventEmitter();
  @Output() previous : EventEmitter<FormGroup> = new EventEmitter();
  formPaiement: FormGroup ;
  @Input() isStateMobile: boolean = false;

  billingInfo = {
    nom: 'my name',
    prenom: 'name',
    numero: '8979878979798798',
    date: '09/2020',
    cvv: '222'
  };

  constructor(
    private snackBar : MatSnackBar,
    private logger : LoggerService, private formBuilder: FormBuilder) { 
      this.formPaiement = this.formBuilder.group({
        'numero': ["", Validators.required],
        'expiry': ["", Validators.required],
        'cvc': [null, Validators.required],
        'nom': [null, Validators.required],
        'prenom': ["", Validators.required],
      });
    }

  ngOnInit() {
  }

  /**
   * next stepper
   */
  next() {
    try{
      if(this.formPaiement.valid){
        this.nextStep3.emit(this.formPaiement);
      }
      else{
        this.snackBar.open("Les champs ne sont pas tous valides", 'OK', {
            duration: 5000,
            panelClass: ['warn-snackbar']
        });
      }
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  /**
   * previous stepper
   */
  previousStepper(){
    this.previous.emit();
  }

}
