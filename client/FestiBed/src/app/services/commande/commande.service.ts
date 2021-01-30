import { Injectable } from '@angular/core';
import { HttpParams, HttpResponse, HttpClient } from '@angular/common/http';
import { ApplicationError } from '../exception/application-error';
import { LoggerService } from '../logger/logger.service';
import { MatSnackBar } from '@angular/material';
import { environment } from 'src/environments/environment';
import { FestivalService } from '../festival/festival.service';
import { UserIdleService } from 'angular-user-idle';
import { HebergementService } from '../hebergement/hebergement.service';

@Injectable({
  providedIn: 'root'
})
export class CommandeService {
  totalFinal: number;
  private url = environment.serveurAdd;

  showError = false;
  formErrorText = [];

  /**
   * Stocke l'id du panier
   */
  private id_panier :number;

  /**
   * Stocke les reservations des festivals.
   */
  private festivals = [];

  /**
   * Stocke les reservations des hebergements.
   */
  private hebergements = [];

  get listFestival() : any[]{
    let existing = localStorage.getItem('Festivals') || null;
    let festivals = JSON.parse(existing);

    return this.verifArray(festivals, this.festivals)
      ? this.festivals
      : this.festivals = festivals || [];
  }

  get listHebergement() : any[]{
    let existing = localStorage.getItem('Hebergements') || null;
    let hebergements = JSON.parse(existing);

    return this.verifArray(hebergements, this.hebergements)
      ? this.hebergements
      : this.hebergements = hebergements || [];
  }

  get totalPanier() {
    return this.getTotal() || 0;
  }

  constructor(
    private logger: LoggerService,
    private http: HttpClient,
    private snackBar: MatSnackBar,
    private festivalService : FestivalService,
    private hebergementService : HebergementService,
    private userIdle : UserIdleService) {

       //Start watching for user inactivity.
      this.userIdle.startWatching();
      
      // Start watching when user idle is starting.
      this.userIdle.onTimerStart().subscribe();
      
      // Start watch when time is up.
      this.userIdle.onTimeout().subscribe(() => {
       // this.verifExpiry(30);
       // this.userIdle.startWatching();
      });
  }

  /**
   * Vérifie l'égalité de 2 tableaux.
   * @param array1 : 1er tableau.
   * @param array2 : 2ème tableau.
   * @returns True ssi égaux.
   */
  private verifArray(array1: any[], array2: any[]): boolean {
    try {
      if (!array1 || !array2 || !Array.isArray(array1) || !Array.isArray(array2) || array1.length !== array2.length) {
        return false;
      }

      return array1.findIndex(item1 =>
        array2.findIndex(item2 => item1.id === item2.id && item1.quantite === item2.quantite) < 0) < 0;
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
      return false;
    }
  }

  /**
   * cherche si l'item avec l'id passé en paramètre est dans la liste list
   * @param id
   * @param list
   */
  trouveFestivalDansPanier( id: number,list: any[]) {
    try {
      let trouve = -1;
      list.forEach((item,index) => {
        if(item.id_cs === id) {
          trouve = index;
          return 0;
        }
      });
      return trouve;

    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  /**
   * calcul du prix total de la commande
   */
  getTotal(): any {
    try {
      let total = 0.00;

      this.listFestival.forEach(res => {
        total += res.montant;
      });

      this.listHebergement.forEach(res => {
        total += res.montant ;
      });
      return total;
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  /**
   * ajout d'un festival dans le panier 
   * avec vérification si il est deja dans le panier
   * @param festival
   */
  async addFestival(soiree:any, cs: any) {
    try {
      return new Promise((resolve, reject) => {
        let reponse: boolean;
        const f: any = {
          "id_cs": cs.id_cs,
          "nbr_places": cs.nbr_places,
          "montant": cs.montant,    
          "date_resa": cs.date_resa
        };
        let festivals = this.listFestival;

        const index = this.trouveFestivalDansPanier(cs.id_cs, festivals);
        if (index >= 0) {
          //si trouve
          //festivals[index].nbr_places = festivals[index].nbr_places + cs.nbr_places;
          //festivals[index].montant = festivals[index].montant + cs.montant;
          //festivals[index].date_resa = cs.date_resa;
          reponse = true;
          resolve(reponse); 
        } 
        else { //si pas trouve
          let concat = {
            "id_festival" : soiree.id_festival,
            "nom": soiree.nom,
            "date_soiree" : cs.date_soiree,
            "categorie" : cs.categorie,
          };
          festivals.push(Object.assign(concat, f));
          reponse = false;
          

          this.festivalService.postFestivalReserver(
            {
              "session_token": localStorage.getItem('tokenFestibed'),
              "id_cs"       : cs.id_cs,
              "nbr_places"  : cs.nbr_places,
              "montant"     : cs.montant + ""
            }
          ).then((res) => {
            let data = JSON.parse(res.body);
            console.log(data);
            if(this.id_panier === null){
              this.id_panier = data.id_panier;
            }
            this.setFestivals(festivals);
            resolve(reponse); 
          }, err =>{
            console.error(err);
            reject(err);
          });
        }

      });
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

   /**
   * ajout d'un festival dans le panier 
   * avec vérification si il est deja dans le panier
   * @param festival
   */
  async addHebergement(logement: any) {
    try {
      return new Promise((resolve, reject) => {
        let reponse: boolean;
        let config: any = {
          "dateArrivee" : logement.dateArrivee,
          "dateDepart" : logement.dateDepart,
          "nbrEnfant" : logement.nbrEnfant,
          "nbrAdulte" : logement.nbrAdulte,
          "montant" : logement.montant,
          "tokenSession" : logement.tokenSession,
          "logementid" : logement.logementid
        };
        let logementList = this.listHebergement;

        logementList.push(Object.assign(logement));

          this.hebergementService.postHebergementReserver({
            "dateArrivee" : logement.dateArrivee,
            "dateDepart" : logement.dateDepart,
            "nbrEnfant" : logement.nbrEnfant,
            "nbrAdulte" : logement.nbrAdulte,
            "montant" : logement.montant,
            "tokenSession" : logement.tokenSession,
            "logementid" : logement.logementid
          }).then((res) => {
            let data = JSON.parse(res.body);
            console.log(data);
            if(this.id_panier === null){
              this.id_panier = data.id_panier;
            }
            this.setHebergements(logementList);
            resolve(reponse); 
          }, err =>{
            console.error(err);
            reject(err);
          });
        }
      );
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  /**
   * post une commande vers le serveur pour son enregistrement
   * @param params correspond aux données d'une commande
   */
  validate(params: { [key: string]: string }): Promise<HttpResponse<string>> {
    const P = new HttpParams({fromObject: params});
    return this.http.post(`${this.url}commande`, P, {
      observe: 'response',
      responseType: 'text',
      headers: {'content-type': 'application/x-www-form-urlencoded'}
    }).toPromise();
  }

  private async get<T>(url: string, data: object): Promise<HttpResponse<T>> {
    return this.http.get<T>(url, {
      observe: 'response',
    }).toPromise();
  }

  /**
   * reinitialise le panier
   */
  resetPanier() {
    try {
      localStorage.removeItem('Festivals');
      localStorage.removeItem('Hebergements');
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  /**
   * ajoute un festival dans le local storage
   * @param festivals
   */
  setFestivals(festivals: any) {
    let existing = JSON.stringify(festivals);
    localStorage.setItem('Festivals', existing);
  }

  /**
   * ajoute un hebergements dans le locale storage
   * @param hebergements
   */
  setHebergements(hebergements: any) {
    let existing = JSON.stringify(hebergements);
    localStorage.setItem('Hebergements', existing);
  }


  /**
   * supprime un festival dans le panier
   * @param index
   */
  removeFestival(index: number) {
    try {
      let festivals = this.listFestival;
      festivals.splice(index, 1);
      this.setFestivals(festivals);
      this.snackBar.open("Le festival est supprimé de votre panier", 'OK', {
        duration: 5000,
        panelClass: ['success-snackbar']
      });
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  /**
   * supprime un hebergement dans le panier
   * @param index
   */
  removeHebergement(index: number) {
    try {
      let hebergement = this.listHebergement;
      hebergement.splice(index, 1);
      this.setHebergements(hebergement);
      this.snackBar.open("Le logement est supprimé de votre panier", 'OK', {
        duration: 5000,
        panelClass: ['success-snackbar']
      });
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  /**
    * retourne les festivals du panier d'un client donnée
    * @param id_user correspond à l'id du client
    * @param options
    */
   async getFestivalPanier(id_user: string, id_panier: string, options?: any): Promise<any[]> {
    try {
      const url = `${this.url}panier?id_user=${id_user}&id_panier=${id_panier}`;
      const res = await this.get<any[]>(url, options);
      return res.body;

    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  /**
   * on va chercher dans le festival, les hebergements correspondant
   */
  verifPanier(){
    let festivals = this.listFestival;
    let hebergements = this.listHebergement;
    let reponse = 0;

    if(festivals.length > 0 && hebergements.length > 0){

      let i=0;
      let j, nbr_places, date_soiree, personnes, date_dep, date_arr;
      while(reponse === 0 && i < festivals.length){
        nbr_places =festivals[i].nbr_places ;
        date_soiree = new Date(festivals[i].date_soiree) ;
        j = 0;
        while(reponse === 0 && j < hebergements.length){
          personnes =  Number(hebergements[j].nbrEnfant + hebergements[j].nbrAdulte);
          date_dep =  new Date(hebergements[j].dateDepart);
          date_arr =  new Date(hebergements[j].dateArrivee);
          if( Number(nbr_places) !==  Number(personnes) ){
            console.log("places incoherents");
            reponse = 1;
          }
          if(date_arr > date_soiree || date_dep < date_soiree){
            console.log("dates incoherents");
            reponse = 2;
          }
          j++;
        }
        i++;
      }
    }
    else{
      reponse = -1;
    }
    return reponse;
  }

  stop() {
    this.userIdle.stopTimer();
  }
 
  stopWatching() {
    this.userIdle.stopWatching();
  }
 
  startWatching() {
    this.userIdle.startWatching();
  }
 
  restart() {
    this.userIdle.resetTimer();
  }

  verifExpiry(minutes){
    let currentDate = new Date();
    console.log("currentDate",currentDate);
    let festivals = this.listFestival;
    if(festivals){
      festivals.forEach( (res,index) => {
        let date = new Date(res.date_resa.getTime() + minutes*60000);

        console.log("date",date);
        if(date < currentDate){
          this.removeFestival(index);
          this.showError= true;
          this.formErrorText.push(res.nom);
          console.log("expiry");
        }
      });
    }

    let herbergements = this.listHebergement;
    if(herbergements){
      herbergements.forEach( (res,index) => {
        let date = new Date(res.date_resa.getTime() + minutes*60000);
        if(date < currentDate){
          this.removeHebergement(index);
          this.showError= true;
          this.formErrorText.push(res.nom);
          console.log("expiry");
          console.log("expiry");
        }
      });
    }
  }
 
}
