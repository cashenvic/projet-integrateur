export interface ReservationBedInterface {
    id_logement: number;
    id_panier?: number;
    montant?: number;
    date_arrivee: Date;
    date_depart: Date;
    nb_adulte: number;
    nb_enfant: number;
    status?: String;
    date: Date;
}