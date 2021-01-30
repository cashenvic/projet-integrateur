export interface ReservationBilletInterface {
    id_cs: number;
    id_panier?: number;
    montant?: number;
    nb_place: number;
    date: Date;
}
