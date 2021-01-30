import { Component, OnInit, ViewChild } from '@angular/core';
import { ApplicationError } from 'src/app/services/exception/application-error';
import { CommandeService } from 'src/app/services/commande/commande.service';
import { MatDialog, MatSnackBar } from '@angular/material';
import { LoggerService } from 'src/app/services/logger/logger.service';
import { HebergementService } from 'src/app/services/hebergement/hebergement.service';
import { SpinnerService } from 'src/app/services/spinner/spinner/spinner.service';
import { ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder } from '@angular/forms';
import * as moment from 'moment';
import { DragScrollComponent } from 'ngx-drag-scroll';

@Component({
  selector: 'app-bed',
  templateUrl: './bed.component.html',
  styleUrls: ['./bed.component.scss']
})
export class BedComponent implements OnInit {
  @ViewChild('nav', {read: DragScrollComponent, static: true}) ds: DragScrollComponent;
  form: FormGroup;
  hebergement : any = {
    id_hebergement: null,
    latitude: null,
    longitude: null,
    nom:null,
    adresse: null,
    date_creation: null,
    email: null,
    telephone: null,
    type: null,
    nombre_etoile: null,
    note_global: null,
  };
  configMap :any;
  id: number;
  init = true;

  constructor( public dialog: MatDialog, 
    private commande : CommandeService, 
    private spinner : SpinnerService,
    private logger : LoggerService,
    private hebergementService : HebergementService,
    private route : ActivatedRoute,
    private snackBar : MatSnackBar,
    fb: FormBuilder) {

    if(this.init){
      this.spinner.show();
    }
    //récuperation de l'id passé sur le router
    const param = this.route.snapshot.params['id'];
    this.id = param;

    const today = new Date(Date.now());
    this.form = fb.group({
      dateRange: [{ begin: new Date(today.getFullYear(), today.getMonth(), today.getDate()), end: new Date(today.getFullYear(), today.getMonth(), today.getDate() + 3) }],
      personnes: 1,
    });
   }

  ngOnInit() {
    try {
      this.getHebergement();
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  public async getHebergement() {
    try {
      //récuperation des données des soirees selon id passé en paramètre
      this.hebergementService.getHebergementById(this.id).then( async data => {
        this.hebergement = data;
        console.log(data);
        this.configMap = {
          position:
          {
            lat: parseFloat(this.hebergement.latitude),
            lng: parseFloat(this.hebergement.longitude)
          },
          label:
            {
            text: this.hebergement.nom,
            color: "white"
          },
          title: this.hebergement.nom,
          info: this.hebergement.nom ,
          options: {
            animation: google.maps.Animation.BOUNCE,
          },
        },

        this.init = false;
        this.spinner.hide();
      });
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  async addBeb(){
    this.spinner.show();
    await this.commande.addHebergement(
      { 
        nom : this.hebergement.nom,
        dateArrivee : moment(this.form.value.dateRange.begin).format('YYYY-MM-DD'),
        dateDepart : moment(this.form.value.dateRange.end).format('YYYY-MM-DD'),
        nbrEnfant : 0,
        nbrAdulte : this.form.value.personnes,
        montant : this.hebergement.tarif * this.form.value.personnes,
        tokenSession : localStorage.getItem('tokenFestibed'),
        logementid : 52
    })
    .then( res => {
        this.snackBar.open('Le logement a été ajouté dans votre panier.', 'OK', {
          duration: 15000,
          panelClass: ['success-snackbar']
        });

        this.spinner.hide();
      })
    .catch(reason => {
      console.error(reason);
      this.spinner.hide();
    });
  }
}
