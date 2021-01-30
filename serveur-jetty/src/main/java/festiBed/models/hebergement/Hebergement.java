package festiBed.models.hebergement;

import festiBed.models.Localisation;
import festiBed.models.logement.Logement;
import festiBed.models.statut.HebergementStatut;
import festiBed.models.utilisateurs.Hebergeur;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Hebergement {
	protected int id_hebergement;
	protected String nom;
	protected String adresse;
	protected String email;
	protected String site_web;
	protected String telephone;
	protected double note_globale;
	protected Date date_creation;
	protected HebergementStatut status;
	protected int nbr_etoile;
	protected String type;


	private List<AvisHebergement> avis = new ArrayList<>();
	private List<PhotoHebergement> photos = new ArrayList<>();
	private Hebergeur hebergeur;
	protected Localisation localisation;
	private List<Logement> logement = new ArrayList<>();

	public Hebergement() {}

	public Hebergement(int id_hebergement, String nom, String adresse, String email, String site_web, String telephone, double note_globale, Date date_creation, HebergementStatut status, int nbr_etoile, String type) {
		this.id_hebergement = id_hebergement;
		this.nom = nom;
		this.adresse = adresse;
		this.email = email;
		this.site_web = site_web;
		this.telephone = telephone;
		this.note_globale = note_globale;
		this.date_creation = date_creation;
		this.status = status;
		this.nbr_etoile = nbr_etoile;
		this.type = type;
	}

	public Hebergement(int id_hebergement, Localisation loc, String nom, String adresse, String email, String site_web, String telephone, double note_globale, Date date_creation, int nbr_etoile, String type) {
		this.id_hebergement = id_hebergement;
		this.nom = nom;
		this.adresse = adresse;
		this.email = email;
		this.site_web = site_web;
		this.telephone = telephone;
		this.note_globale = note_globale;
		this.date_creation = date_creation;
		this.nbr_etoile = nbr_etoile;
		this.type = type;
		this.localisation = loc;
	}

	public int getId_hebergement() {
		return id_hebergement;
	}

	public void setId_hebergement(int id_hebergement) {
		this.id_hebergement = id_hebergement;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSite_web() {
		return site_web;
	}

	public void setSite_web(String site_web) {
		this.site_web = site_web;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public double getNote_globale() {
		return note_globale;
	}

	public void setNote_globale(double note_globale) {
		this.note_globale = note_globale;
	}

	public Date getDate_creation() {
		return date_creation;
	}

	public void setDate_creation(Date date_creation) {
		this.date_creation = date_creation;
	}

	public HebergementStatut getStatus() {
		return status;
	}

	public void setStatus(HebergementStatut status) {
		this.status = status;
	}

	public int getNbr_etoile() {
		return nbr_etoile;
	}

	public void setNbr_etoile(int nbr_etoile) {
		this.nbr_etoile = nbr_etoile;
	}

	public List<AvisHebergement> getAvis() {
		return avis;
	}

	public void setAvis(List<AvisHebergement> avis) {
		this.avis = avis;
	}

	public List<PhotoHebergement> getPhotos() {
		return photos;
	}

	public void setPhotos(List<PhotoHebergement> photos) {
		this.photos = photos;
	}

	public Hebergeur getHebergeur() {
		return hebergeur;
	}

	public void setHebergeur(Hebergeur hebergeur) {
		this.hebergeur = hebergeur;
	}

	public Localisation getLocalisation() {
		return localisation;
	}

	public void setLocalisation(Localisation localisation) {
		this.localisation = localisation;
	}

	public List<Logement> getLogement() {
		return logement;
	}

	public void setLogement(List<Logement> logement) {
		this.logement = logement;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
