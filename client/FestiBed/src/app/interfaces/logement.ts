export interface LogementInterface {
    id_logement: number;
    id_hebergement: number;
    date_creation: Date;
    status?:String;
    type :LTypeInterface
}

export enum LTypeInterface {
    ChambreSimple = 0,
    ChambreDouble = 1,
    ChambreFamilial = 2,
    Dortoir = 3,
    Appartement =4
}