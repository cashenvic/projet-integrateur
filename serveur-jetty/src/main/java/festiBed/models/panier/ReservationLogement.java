package festiBed.models.panier;

import java.util.Date;

import festiBed.models.logement.Logement;
import festiBed.models.statut.ResaBilletStatut;
import festiBed.models.statut.ResaCampingStatut;
import festiBed.utils.Status;

public class ReservationLogement {
	
    private Logement logement;
	private String  tokenSession;

	private int id_logement;

	private int id_panier;

	private java.sql.Date date_creation;

	private int nbr_adulte;
    
	private int nbr_enfant;

	private Date date_arrivee;
	private Date date_depart;

	private double montant;
    
	private String status;
    
    public ReservationLogement() {}

	public ReservationLogement(ReservationLogement reservation_logement_key, Logement logement, String tokenSession,
			int nbr_adulte, int nbr_enfant, Date date_arrivee, Date date_depart,
			double montant, String status) {
		super();
		this.logement = logement;
		this.tokenSession = tokenSession;
		this.nbr_adulte = nbr_adulte;
		this.nbr_enfant = nbr_enfant;
		this.date_arrivee = date_arrivee;
		this.date_depart = date_depart;
		this.montant = montant;
		this.status = status;
	}

	public ReservationLogement(java.sql.Date date_arrivee, java.sql.Date date_depart,
							   int nbr_enfant, int nbr_adulte,
							   double montant, String tokenSession, int id_logement) {
		this.date_arrivee = date_arrivee;
		this.date_depart = date_depart;
		this.nbr_enfant = nbr_enfant;
		this.nbr_adulte = nbr_adulte;
		this.montant = montant;
		this.tokenSession = tokenSession;
		this.id_logement = id_logement;
		this.status = ResaCampingStatut.Traitement.getValue();
	}

	public Logement getLogement() {
		return logement;
	}

	public void setLogement(Logement logement) {
		this.logement = logement;
	}

	public String getTokenSession() {
		return tokenSession;
	}

	public void setTokenSession(String tokenSession) {
		this.tokenSession = tokenSession;
	}

	public int getNbr_adulte() {
		return nbr_adulte;
	}

	public void setNbr_adulte(int nbr_adulte) {
		this.nbr_adulte = nbr_adulte;
	}

	public int getNbr_enfant() {
		return nbr_enfant;
	}

	public void setNbr_enfant(int nbr_enfant) {
		this.nbr_enfant = nbr_enfant;
	}

	public Date getDate_arrivee() {
		return date_arrivee;
	}

	public void setDate_arrivee(Date date_arrivee) {
		this.date_arrivee = date_arrivee;
	}

	public Date getDate_depart() {
		return date_depart;
	}

	public void setDate_depart(Date date_depart) {
		this.date_depart = date_depart;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId_logement() {
		return id_logement;
	}

	public void setId_logement(int id_logement) {
		this.id_logement = id_logement;
	}

	public int getId_panier() {
		return id_panier;
	}

	public void setId_panier(int id_panier) {
		this.id_panier = id_panier;
	}

	public java.sql.Date getDate_creation() {
		return date_creation;
	}

	public void setDate_creation(java.sql.Date date_creation) {
		this.date_creation = date_creation;
	}


	@Override
	public String toString(){
    	return "{" +
				" \"id_logement\":\"" + id_logement + "\"" +
				", \"id_panier\":\"" + id_panier + "\"" +
				", \"date_arrivee\":\"" + date_arrivee + "\"" +
				", \"date_depart\":\"" + date_depart + "\"" +
				", \"nbr_adulte\":\"" + nbr_adulte + "\"" +
				", \"nbr_enfant\":\"" + nbr_enfant + "\"" +
				", \"montant\":\"" + montant + "\"" +
				", \"date_creation\":\"" + date_creation + "\"" +
				'}';
	}

}
