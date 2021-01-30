import { Injectable } from '@angular/core';
import { LoggerService } from './logger.service';

@Injectable({
  providedIn: 'root'
})
/** Logger vers la console. */
export class ConsoleLoggerService extends LoggerService {
  /** Instance de sortie. */
  console: Console = console;

  /** Logger vers la console. */
  constructor() {
    super();
  }

  /** Trace une information lorsque le mode Debug est activé.
   * @param message : objet à tracer. Note : s'il s'agit d'une fonction, elle sera exécutée.
   * @param optionalParams : paramètres supplémentaires.
   */
  debug(message: any, ...optionalParams: any[]): void {
    if (this.isDebugMode) {
      this.log(message, ...optionalParams);
    }
  }

  /** Trace une information.
   * @param message : objet à tracer. Note : s'il s'agit d'une fonction, elle sera exécutée.
   * @param optionalParams : paramètres supplémentaires.
   */
  log(message: any, ...optionalParams: any[]): void {
    if (message && typeof(message) === 'function') { // Function => récupération du message
      message = message();
    }

    if (!message) { // Pas de message => abandon
      return;
    }

    this.console.log(message, ...optionalParams);
  }

  /** Trace un warning.
   * @param message : objet à tracer. Note : s'il s'agit d'une fonction, elle sera exécutée.
   * @param optionalParams : paramètres supplémentaires.
   */
  warn(message: any, ...optionalParams: any[]): void {
    if (message && typeof(message) === 'function') { // Function => récupération du message
      message = message();
    }

    if (!message) { // Pas de message => abandon
      return;
    }

    this.console.warn(message, ...optionalParams);
  }

  /** Trace une erreur.
   * @param error : erreur à tracer. Note : s'il s'agit d'une fonction, elle sera exécutée.
   */
  error(error: Error | (() => Error)): void {
    if (error && typeof(error) === 'function') { // Function => récupération du message
      error = error();
    }

    if (!error) { // Pas de message => abandon
      return;
    }

    this.console.error(error);
  }

  /** Vide la console. */
  clear(): void {
    console.clear();
  }
}
