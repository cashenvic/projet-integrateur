import { DomaineInterface } from './domaine';
import { PhotoInterface } from './photo';
import { AvisInterface } from './avis';

export interface FestivalInterface {
    id_festival: number;
    nom: string;
    date_debut: string;
    date_fin: string;
    date_creation?: string;
    note_global: string;
    description?: string;
    status?: string;
    latitude?: string;
    longitude?: string;
    id_domaines?: Array<DomaineInterface>;
    id_user?: number;
    site_web?: string;
    photos?: Array<PhotoInterface>;
    avis?: Array<AvisInterface>;
}
