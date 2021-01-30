import { AjaxError } from 'rxjs/ajax';

/** Erreur applicative. */
export class ApplicationError extends Error {
    /** Date de l'erreur. */
    readonly date: Date;

    /** Erreur parente. */
    innerError: Error = null;

    /** Message destiné à l'utilisateur. */
    messageForUser: string = null;

    /** Instancie une erreur.
     * @param message : message de l'erreur.
     */
    constructor(message: string, ...others) {
        super(...[message || '', ...others]);

        this.name = 'ApplicationError';
        this.date = new Date();
        this.messageForUser = null;
    }

    /** Retourne la pile des erreurs, de la plus profonde à celle-ci. */
    get errors(): Error[] {
        return ApplicationError.getErrors(this);
    }

    /** Retourne la pile des erreurs, de la plus profonde à celle indiquée.
     * @param errorApp : Erreur à scanner.
     */
    static getErrors(errorApp: ApplicationError): Error[] {
        const errors: Error[] = [errorApp];
        let currentError: Error = errorApp;

        while ((currentError as ApplicationError).innerError) {
            currentError = (currentError as ApplicationError).innerError;
            errors.unshift(currentError);
        }

        return errors;
    }

    /** Encapsule une erreur.
     * @param error : erreur à encapsuler.
     * @param message : message d'erreur.
     * @param messageForUser : message d'erreur à afficher à l'utilisateur.
     */
    static wrappeError(error: Error, message?: string, messageForUser?: string): ApplicationError {
        const isApplicationError = error.name === 'ApplicationError';
        if (!message && !messageForUser && isApplicationError) { // Erreur déjà wrappée => retour
            return error as ApplicationError;
        }

        // Constitution du message
        error = error || new Error('Erreur non spécifiée.');
        const innerErrors = ApplicationError.getErrors(error as ApplicationError);
        const errorMessage = innerErrors
            // Récupération du 1er message
            ? (innerErrors[0] || error).message
            : error.message;

        if (message) {
            message += `
                Détail : '${errorMessage}'.`;
        } else {
            message = errorMessage;
        }

        // Wrappe
        const errorApp = new ApplicationError(message);
        errorApp.innerError = error;

        // Message pour l'utilisateur
        if (messageForUser) {
            errorApp.messageForUser = messageForUser;
        } else if (isApplicationError && (error as ApplicationError).messageForUser) { // Message du serveur pour affichage
            errorApp.messageForUser = (error as ApplicationError).messageForUser;
        }

        return errorApp;
    }

    /** Encapsule une erreur ajax.
     * @param urlMethod : URL de la requête.
     * @param error : erreur à encapsuler.
     * @param detail : détail sur l'erreur.
     * @param messageForUser : message d'erreur à afficher à l'utilisateur.
     */
    static wrappeAjax(urlMethod: string, error: AjaxError, detail?: string, messageForUser?: string): ApplicationError {
        if (error) {
            const message = `Requête sur '${urlMethod || null}' => KO ! =>
                statut='${error.status}',
                message='${error.message}',
                réponse='${JSON.stringify(error.response)}',
                description='${detail || null}'.`;

            return ApplicationError.wrappeError(error, message, messageForUser);
        }

        return new ApplicationError(detail);
    }
}
