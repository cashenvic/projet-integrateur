import { LocalisationInterface } from './localisation';
import { AvisInterface } from './avis';
import { PhotoInterface } from './photo';

export interface HebergementInterface {
    id_hebergement: number;
    nom: string;
    adresse?: string;
    date_creation: Date;
    email?: string;
    site_web?: string;
    localisation: LocalisationInterface;
    id_user: number;
    note_globale?: number;
    nb_etoile?:number;
    photos?: Array<PhotoInterface>;
    avis?: Array<AvisInterface>;
    type : HTypeInterface;
    status?:String;
}

export enum HTypeInterface {
    Hotel = 0,
    VillageVacances =1,
    Camping = 2,
    Residence = 3
}