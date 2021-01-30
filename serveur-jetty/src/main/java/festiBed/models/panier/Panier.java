package festiBed.models.panier;

import festiBed.models.statut.PanierStatut;

import java.sql.Date;

public class Panier {
	private int id_panier;
	private double montant_total;
	
	private Date date_creation;
	
	private PanierStatut status;
	
	private int id_user;
	
	public Panier() {}

	public Panier(int id_panier, double montant_total, PanierStatut status, int id_user, Date date_creation) {
		this.id_panier = id_panier;
		this.montant_total = montant_total;
		this.status = status;
		this.id_user = id_user;
		this.date_creation = date_creation;
	}

	public int getId_panier() {
		return id_panier;
	}

	public void setId_panier(int id_panier) {
		this.id_panier = id_panier;
	}

	public double getMontant_total() {
		return montant_total;
	}

	public void setMontant_total(double montant_total) {
		this.montant_total = montant_total;
	}

	public Date getDate_creation() {
		return date_creation;
	}

	public PanierStatut getStatus() {
		return status;
	}

	public void setStatus(PanierStatut status) {
		this.status = status;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public void setDate_creation(Date date_creation) {
		this.date_creation = date_creation;
	}

	@Override
	public String toString() {
		return "{" +
				"\"id_panier\": \"" + id_panier + "\""+
				", \"montant_total\": \"" + montant_total + "\""+
				", \"date_creation\": \"" + date_creation + "\""+
				", \"status\": \"" + status + "\""+
				", \"id_user\": \"" + id_user + "\""+
				'}';
	}
}
