import { Injectable, Inject } from '@angular/core';
import { ajax } from 'rxjs/ajax';
import { ConfigData } from './config-data';
import { retry } from 'rxjs/operators';
import { LoggerService } from '../logger/logger.service';
import { ApplicationError } from '../exception/application-error';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
/** Charge la configuration paramétrable de l'application. */
export class ConfigurableConfigurationService {
  /** Retourne (une promise sur) la configuration applicative. */
  readonly config: Promise<ConfigData> = null;

  /** Charge la configuration paramétrable de l'application. */
  constructor(
    protected logger: LoggerService
  ) {
    try {
      const configUrl = '/assets/config/config.json';
      this.config = ajax.getJSON<ConfigData>(configUrl)
        .pipe(retry(2)) // 3 tentatives
        .toPromise()
        .catch(error => { // Erreur => trace
          const message = 'Impossible de charger la configuration.';
          const errorApp = ApplicationError.wrappeAjax(configUrl, error, message);
          this.logger.error(errorApp);
          return Promise.reject(errorApp);
        });

      if (!environment.production) { // Pour le Dévelop => pointe la machine de Dév
        this.config = this.config.catch(() => {
          return {
            serverBackEndUrl: 'http://p14cmzbi111:8080/lucide/'
          } as ConfigData;
        });
      }

      // Information de Dévelop
      this.config.then(config => {
        if (config) {
          this.logger.debug(() => `Configuration=${JSON.stringify(config)}`);
        }
      });
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);
    }
  }

  /** Retourne (une promise sur) l'URL des API. */
  get apiUrl(): Promise<string> {
    try {
      return this.config.then(config => config.serverBackEndUrl);
    } catch (error) {
      const errorApp = ApplicationError.wrappeError(error);
      this.logger.error(errorApp);

      return Promise.reject(errorApp);
    }
  }
}
