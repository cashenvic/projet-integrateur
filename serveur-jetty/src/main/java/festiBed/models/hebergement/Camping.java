package festiBed.models.hebergement;

import festiBed.models.Localisation;
import festiBed.models.statut.HebergementStatut;

import java.util.Date;

public class Camping extends Hebergement {

    private double tarif;
    private int id_logement;
    private int capacite;

    public Camping() {
    }

    public Camping(int id_hebergement, String nom, String adresse, String email, String site_web, String telephone,
                   double note_globale, Date date_creation, HebergementStatut status, int nbr_etoile, String type, double tarif,
        int id_logement, int capacite) {
        super(id_hebergement,nom,adresse,email,site_web,telephone,note_globale,date_creation,status,nbr_etoile,type);
        this.tarif = tarif;
        this.id_logement = id_logement;
        this.capacite = capacite;
    }

    public Camping(int id_hebergement, Localisation loc, String nom, String adresse, String email, String site_web,
                   String telephone, double note_globale, Date date_creation, int nbr_etoile, String type, double tarif,
                   int id_logement, int capacite) {
        super(id_hebergement, loc, nom, adresse, email, site_web, telephone, note_globale,  date_creation,  nbr_etoile, type);
        this.tarif = tarif;
        this.id_logement = id_logement;
        this.capacite = capacite;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }


    public int getId_logement() {
        return id_logement;
    }

    public void setId_logement(int id_logement) {
        this.id_logement = id_logement;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id_hebergement\":\"" + id_hebergement + "\"" +
                ", \"id_logement\":\"" + id_logement + "\"" +
                ", \"latitude\":\"" + localisation.getLatitude() + "\"" +
                ", \"longitude\":\"" + localisation.getLongitude() + "\"" +
                ", \"nom_departement\":\"" + localisation.getNom_departement() + "\"" +
                ", \"nom\":\"" + nom + "\"" +
                ", \"adresse\":\"" + adresse + "\"" +
                ", \"tarif\":\"" + tarif + "\"" +
                ", \"email\":\"" + email + "\"" +
                ", \"site_web\":\"" + site_web +  "\"" +
                ", \"telephone\":\"" + telephone + "\"" +
                ", \"note_globale\":\"" + note_globale + "\"" +
                ", \"date_creation\":\"" + date_creation + "\"" +
                ", \"nombre_etoile\":\"" + nbr_etoile + "\"" +
                ", \"type\":\"" + type +  "\"" +
                ", \"capacite\":\"" + capacite +  "\"" +
                '}';
    }
}
