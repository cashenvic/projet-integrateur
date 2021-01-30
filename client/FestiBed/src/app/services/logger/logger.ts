/** Logger. */
export interface Logger {
    /** Trace une information lorsque le mode Debug est activé.
     * @param message : objet à tracer. Note : s'il s'agit d'une fonction, elle sera exécutée.
     * @param optionalParams : paramètres supplémentaires.
     */
    debug(message: any, ...optionalParams: any[]): void;

    /** Trace une information.
     * @param message : objet à tracer. Note : s'il s'agit d'une fonction, elle sera exécutée.
     * @param optionalParams : paramètres supplémentaires.
     */
    log(message: any, ...optionalParams: any[]): void;

    /** Trace un warning.
     * @param message : objet à tracer. Note : s'il s'agit d'une fonction, elle sera exécutée.
     * @param optionalParams : paramètres supplémentaires.
     */
    warn(message: any, ...optionalParams: any[]): void;

    /** Trace une erreur.
     * @param error : erreur à tracer. Note : s'il s'agit d'une fonction, elle sera exécutée.
     */
    error(error: Error): void;
}
