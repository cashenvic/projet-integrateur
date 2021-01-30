export interface SoireeInterface {
    id_soiree: number;
    date_soiree: Date;
    status?: string;
    id_festival: number;
    nbr_places_total?: number
    nb_categorie?: number
    cs? : Array<any>
}
