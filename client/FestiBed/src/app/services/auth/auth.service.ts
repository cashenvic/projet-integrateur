import { Injectable } from '@angular/core';
import * as firebase from 'firebase/app';
import { environment } from 'src/environments/environment';
import { UtilisateurInterface } from 'src/app/interfaces/utilisateur';
import { AngularFireAuth } from '@angular/fire/auth';
import { HttpClient,HttpParams, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoggerService } from '../logger/logger.service';
import { ApplicationError } from '../exception/application-error';
import { SpinnerService } from '../spinner/spinner/spinner.service';

function AlxToObjectString(data?: object): { [key: string]: string } {
  const res = {};
  for (const k of Object.keys(data || {})) {
    const v = data[k];
    res[k] = typeof v === 'string' ? v : JSON.stringify(v);
  }
  return res;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAuth: boolean;
  private url = environment.serveurAdd;

  authenticateduser : UtilisateurInterface ;

  get user(){
    let existing = localStorage.getItem('user') || null;
    let user = JSON.parse(existing);
    if(user) {
      this.authenticateduser = user;
      this.isAuth = true;
    }
    return this.authenticateduser || null;
  }

  constructor(private afAuth: AngularFireAuth, 
    private http: HttpClient,
    private spinner: SpinnerService, 
    private logger :LoggerService,
    private router: Router) {
  
  }

  /**
   * To check if a user is signed in or not
   */
  infoUser() {
    try{
      this.spinner.show();
      this.afAuth.user.subscribe(
        user => {
          if (user) {
            this.isAuth = true;
            let existing = localStorage.getItem('user');
            let utilisateur = JSON.parse(existing);
            this.authenticateduser = utilisateur;
          }
          else {
            this.isAuth = false;
          }
        });
    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    } finally {
      this.spinner.hide();
    }
  }

  /**
   * Login method using firebase email/password policy
   * @param email
   * @param password
   */
  async loginMail(email: string, password: string, type:string) {
    try{
      let i, nom, prenom;
      return new Promise((resolve, reject) => {
        this.afAuth.auth.signInWithEmailAndPassword(email, password)
          .then(res => {
            console.log('logged in');
            if (res.user.displayName) {
              let i = res.user.displayName.indexOf(" ");
              nom = res.user.displayName.substr(0, i),
              prenom = res.user.displayName.substr(i);
            } else {
              nom = '';
              prenom = '';
            }
            /////////////////// DB authentication call
            this.authentificateOnDB({
              token_firebase: res.user.uid,
              token_session: localStorage.getItem('tokenFestibed'),
              nom: nom,
              prenom: prenom,
              mail: res.user.email,
              type : type
            }).then((res) => {
              let user = JSON.parse(res.body);
              resolve();

              this.isAuth = true;
              this.authenticateduser = {
                id_user: user.id_user,
                token_firebase: user.token_firebase,
                token_session: user.token_session,
                prenom: user.prenom,
                nom: user.nom,
                mail: user.mail,
                statut: user.statut,
                type: user.type
              };
              localStorage.setItem('user', JSON.stringify(this.authenticateduser));

          }, err =>{
            console.error('Error during connexion mail on DB');
            console.error(err);
            reject(err);
          });
          /////////////////// End DB authentication call

            resolve(res);
          }, err => {
            reject(err);
          })
      });
    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    } 

  }

  // deconnection de l'utilisateur
  async doLogout() {
    try{
      this.spinner.show();
      await this.afAuth.auth.signOut().then(
        res => {
          this.authenticateduser = null;
          this.isAuth = false;
          localStorage.removeItem('user');
        }, err => {
          console.log(err);
        }
      );
    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    } finally {
      this.spinner.hide();
    }
  }

  /**
   * Subscription using firebase email/password method
   */
  async inscription(user:any) {
    try{
      this.spinner.show();
    
      return new Promise((resolve, reject) => {
        let i;
        firebase.auth().createUserWithEmailAndPassword(user.email, user.password).then(res => {
          console.log('Account added on firebase');

          res.user.updateProfile({
            displayName: user.nom + " " + user.prenom
          });

          this.authentificateOnDB({
            token_firebase: res.user.uid,
            token_session: localStorage.getItem('tokenFestibed'),
            nom: user.nom,
            prenom: user.prenom,
            mail: res.user.email,
            type : user.type
          }).then((res) => {
            let user = JSON.parse(res.body);
            this.authenticateduser = {
              id_user: user.id_user,
              token_firebase: user.token_firebase,
              token_session: user.token_session,
              prenom: user.prenom,
              nom: user.nom,
              mail: user.email,
              statut: user.uid,
              type: user.uid,
            };
            resolve();

            this.isAuth = true;
          }, err =>{
            console.error('Error during user creation on DB');
            console.error(err);
            reject(err);
          });

          resolve(res);
        }, err => {
          console.error('Account creation error on firebase');
          console.error(err);
          reject(err);
        })
      });
    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    } finally {
      this.spinner.hide();
    }
  }

  async authentificateOnDB(params: {[key: string]: string}): Promise<HttpResponse<string>> {
    try{
      const P = new HttpParams( {fromObject: params} );
      return this.http.post( `${this.url}login`, P, {
        observe: 'response',
        responseType: 'text',
        headers: {'content-type': 'application/x-www-form-urlencoded'}
      }).toPromise();
    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    } 
  }

  private async get<T>(url: string, data: object): Promise<HttpResponse<T>> {
    return this.http.get<T>(url, {
      observe: 'response',
      params: {...AlxToObjectString(data)}
    }).toPromise();
  }

  // Sign in with Facebook
  async facebookAuth(type:string) {
    try{
      this.spinner.show();
      return new Promise( (resolve, reject) => {
        this.afAuth.auth.signInWithPopup(new firebase.auth.FacebookAuthProvider()).then(res => {
          let i = res.user.displayName.indexOf(" ");
          console.log('Account FB added on firebase');

          this.authentificateOnDB({
            token_firebase: res.user.uid,
            token_session: localStorage.getItem('tokenFestibed'),
            nom: res.user.displayName.substr(0, i),
            prenom: res.user.displayName.substr(i),
            mail: res.user.email,
            type : type
          }).then((res) => {
            let user = JSON.parse(res.body);

            this.authenticateduser = {
              id_user: user.id_user,
              token_firebase: user.token_firebase,
              token_session: user.token_session,
              prenom: user.prenom,
              nom: user.nom,
              mail: user.email,
              statut: user.uid,
              type: user.uid,
            };
            localStorage.setItem('user', JSON.stringify(this.authenticateduser));
            this.isAuth = true;

            resolve();
          }, err =>{
            console.error('Error during FB connexion');
            console.error(err);
            reject(err);
          });
          /////////////////// End DB authentication call

          resolve(res);
        }, err => {
          console.error('Account creation error on firebase');
          console.error(err);
          reject(err);
        })
      });

    }catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    } finally {
      this.spinner.hide();
    }
  }
  
}
