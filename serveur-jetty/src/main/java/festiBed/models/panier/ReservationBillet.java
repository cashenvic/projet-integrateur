package festiBed.models.panier;

import festiBed.models.festival.CategorieSoiree;
import festiBed.models.statut.ResaBilletStatut;

import java.sql.Date;
import java.util.Calendar;

public class ReservationBillet {
    private CategorieSoiree categorie_soiree;
	private Panier panier;

	private int id_panier;
	private int id_cs;
	private int nbr_places;
	private double montant;
	private Date date_resa;
	private Date date_soiree;
	private String status;

	public ReservationBillet() {}

	public ReservationBillet(int id_panier, int id_cs, int nbr_places, double montant) {
		this.id_panier = id_panier;
		this.id_cs = id_cs;
		this.nbr_places = nbr_places;
		this.montant = montant;
		this.status = ResaBilletStatut.Actif.getValue();
		date_resa = new java.sql.Date(Calendar.getInstance().getTime().getTime());
	}

	public CategorieSoiree getCategorie_soiree() {
		return categorie_soiree;
	}

	public void setCategorie_soiree(CategorieSoiree categorie_soiree) {
		this.categorie_soiree = categorie_soiree;
	}

	public Panier getPanier() {
		return panier;
	}

	public void setPanier(Panier panier) {
		this.panier = panier;
	}

	public int getNbr_places() {
		return nbr_places;
	}

	public void setNbr_places(int nbr_places) {
		this.nbr_places = nbr_places;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public Date getDate_resa() {
		return date_resa;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId_panier() {
		return id_panier;
	}

	public void setId_panier(int id_panier) {
		this.id_panier = id_panier;
	}

	public int getId_cs() {
		return id_cs;
	}

	public void setId_cs(int id_cs) {
		this.id_cs = id_cs;
	}

	public Date getDate_soiree() {
		return date_soiree;
	}

	public void setDate_soiree(Date date_soiree) {
		this.date_soiree = date_soiree;
	}

	@Override
	public String toString() {
		return "{" +
				" \"id_panier\":\"" + id_panier + "\"" +
				", \"id_cs\":\"" + id_cs + "\"" +
				", \"nbr_places\":\"" + nbr_places + "\"" +
				", \"montant\":\"" + montant + "\"" +
				", \"date_resa\":\"" + date_resa + "\"" +
				", \"date_soiree\":\"" + date_soiree + "\"" +
				", \"status\":\"" + status + '\'' + "\"" +
				'}';
	}
}
