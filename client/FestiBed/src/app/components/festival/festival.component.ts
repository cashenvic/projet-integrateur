import { Component, OnInit, ViewChild } from '@angular/core';
import {Tile} from '../../interfaces/tile';
import { DragScrollComponent } from 'ngx-drag-scroll';
import { MatDialog, MatSnackBar } from '@angular/material';
import { CommandeService } from 'src/app/services/commande/commande.service';
import { DialogAddComponent } from '../dialog-add/dialog-add.component';
import { Location } from '@angular/common';
import { FestivalService } from 'src/app/services/festival/festival.service';
import { ApplicationError } from 'src/app/services/exception/application-error';
import { LoggerService } from 'src/app/services/logger/logger.service';
import { ActivatedRoute } from '@angular/router';
import { SpinnerService } from 'src/app/services/spinner/spinner/spinner.service';
import { FestivalInterface } from 'src/app/interfaces/festival';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { SoireeInterface } from 'src/app/interfaces/soiree';

@Component({
  selector: 'app-festival',
  templateUrl: './festival.component.html',
  styleUrls: ['./festival.component.scss']
})
export class FestivalComponent implements OnInit {
  @ViewChild('nav', {read: DragScrollComponent, static: true}) ds: DragScrollComponent;
  festival : FestivalInterface = {
    id_festival: null,
    nom: "",
    date_debut: null,
    date_fin: null,
    site_web: null,
    note_global: null,
    latitude: null,
    longitude: null,
    date_creation: null,
    status: null
  };
  configMap :any;
  id: number;
  soirees : Array<SoireeInterface> =[];
  init = true;

  constructor( public dialog: MatDialog, 
    private commande : CommandeService, 
    private spinner : SpinnerService,
    private logger : LoggerService,
    private festivalService : FestivalService,
    private route : ActivatedRoute,
    private snackBar : MatSnackBar) {


    if(this.init){
      this.spinner.show();
    }
    //récuperation de l'id passé sur le router
    const param = this.route.snapshot.params['id'];
    this.id = param;
   }

  ngOnInit() {
    try {
      this.getFestival();
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
    finally{
    }
  }

  /**
   * get festival
   */
  public async getFestival() {
    try {
      //récuperation des données des soirees selon id passé en paramètre
      this.festivalService.getFestivalById(this.id).then( async data => {
        this.festival = data;
        await this.getSoiree().then( 
          res => {
          this.configMap = {
            position:
            {
              lat: parseFloat(this.festival.latitude),
              lng: parseFloat(this.festival.longitude)
            },
            label:
              {
              text: this.festival.nom,
              color: "white"
            },
            title: this.festival.nom,
            info: this.festival.nom ,
            options: {
              animation: google.maps.Animation.BOUNCE,
            },
          };

          this.init = false;
          this.spinner.hide();
          }
        );
        
      });
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  /**
   * get liste des soirees
   */
  public async getSoiree() {
    try {
      return new Promise((resolve, reject) => {
      //récuperation des données des soirees selon id passé en paramètre
       this.festivalService.getSoireeFestivalById(this.festival.id_festival).then( 
         data =>{
          this.soirees = data; 

          data.forEach( (soiree, index) => {
            this.getCategorieSoiree(soiree,index);
          });
          //console.log(this.soirees);
          resolve();
      }, err => {
        reject(err);
      });
    });
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }finally{
    }
  }

  public getCategorieSoiree(soiree,index) {
    try {
      let objet = {
        nbr_places_total : 0,
        nb_categorie : 1,
        cs : []
      };
      //récuperation des données des categorie_soiree selon id passé en paramètre
      this.festivalService.getCategorieSoireeById(soiree.id_soiree).then(
        data => {
          objet.cs = data || [];
          objet.nbr_places_total = objet.cs.reduce( (acc, val) => acc += Number(val.nbr_place_dispo), 0);
          objet.nb_categorie = objet.cs.length;
          this.soirees[index] = Object.assign( soiree , objet);
      });
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }


  moveLeft() {
    this.ds.moveLeft();
  }

  moveRight() {
    this.ds.moveRight();
  }

  moveTo(index) {
    this.ds.moveTo(index);
  }

  addCS(soiree : any, categorie:any){
  
    if(categorie === 0 || ( categorie === 1 && soiree.nb_categorie === 2)){
      const dialogRef = this.dialog.open(DialogAddComponent, {
        width: '400px',
        data: {nb_place_dispo: soiree.cs[categorie].nb_place_dispo, tarif: soiree.cs[categorie].tarif}
      });

      dialogRef.afterClosed().subscribe( async result => {
        this.spinner.show();
        await this.commande.addFestival(
        {
          id_festival : this.festival.id_festival,
          nom: this.festival.nom,
        },
        {
          categorie : soiree.nb_categorie === 2 ? categorie+1 : 0,//avec categorie ou sans 
          date_soiree : soiree.date_soiree,
          id_cs: soiree.cs[categorie].id_cs,
          nbr_places: result,
          montant: Number(result * soiree.cs[categorie].tarif),    
          date_resa: new Date()
        }).then(
          res => {
            if(res){//true deja dans le panier
              this.snackBar.open('Vous avez déjà cette soirée festival.', 'OK', {
                duration: 10000,
                panelClass: ['info-snackbar']
              });
            }
            else{ // false nouveau insert
              this.snackBar.open(result + ' billet(s) pour le festival ' + this.festival.nom + ' a été ajouté dans votre panier.', 'OK', {
                duration: 15000,
                panelClass: ['success-snackbar']
              });
            }
            this.spinner.hide();
          }
        ).catch((reason => {
          console.error(reason);
        }));

      });
    }
  }

}
