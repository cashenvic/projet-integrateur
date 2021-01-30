package festiBed.models.festival;

import festiBed.models.panier.Panier;
import festiBed.models.panier.ReservationBillet;
import festiBed.models.statut.SoireeStatut;

import java.util.List;

public class CategorieSoiree {
	private int id_cs;
	private int nbr_place_dispo;
	private int nbr_place_total;
	private double tarif;
	private int id_festival;
	private int id_soiree;

	private SoireeStatut status;
	private Soiree soiree;
	private List<ReservationBillet> resa_billets;
    private List<Panier> paniers;
	
	public CategorieSoiree() {}

	public CategorieSoiree(int id_cs, int nbr_place_dispo, int nbr_place_total, double tarif, int id_soiree, SoireeStatut status) {
		this.id_cs = id_cs;
		this.nbr_place_dispo = nbr_place_dispo;
		this.nbr_place_total = nbr_place_total;
		this.tarif = tarif;
		this.id_soiree = id_soiree;
		this.status = status;
	}

	public int getId_cs() {
		return id_cs;
	}

	public void setId_cs(int id_cs) {
		this.id_cs = id_cs;
	}

	public int getNbr_place_dispo() {
		return nbr_place_dispo;
	}

	public void setNbr_place_dispo(int nbr_place_dispo) {
		this.nbr_place_dispo = nbr_place_dispo;
	}

	public int getNbr_place_total() {
		return nbr_place_total;
	}

	public void setNbr_place_total(int nbr_place_total) {
		this.nbr_place_total = nbr_place_total;
	}

	public double getTarif() {
		return tarif;
	}

	public void setTarif(double tarif) {
		this.tarif = tarif;
	}

	public SoireeStatut getStatus() {
		return status;
	}

	public void setStatus(SoireeStatut status) {
		this.status = status;
	}

	public Soiree getSoiree() {
		return soiree;
	}

	public void setSoiree(Soiree soiree) {
		this.soiree = soiree;
	}

	public List<ReservationBillet> getResa_billets() {
		return resa_billets;
	}

	public void setResa_billets(List<ReservationBillet> resa_billets) {
		this.resa_billets = resa_billets;
	}

	public int getId_festival() {
		return id_festival;
	}

	public void setId_festival(int id_festival) {
		this.id_festival = id_festival;
	}

	public int getId_soiree() {
		return id_soiree;
	}

	public void setId_soiree(int id_soiree) {
		this.id_soiree = id_soiree;
	}

	@Override
	public String toString() {
		return "{" +
				"\"id_cs\":\"" + id_cs + "\"" +
				", \"nbr_place_dispo\":\"" + nbr_place_dispo + "\"" +
				", \"nbr_place_total\":\"" + nbr_place_total + "\"" +
				", \"tarif\":\"" + tarif + "\"" +
				", \"id_soiree\":\"" + id_soiree + "\"" +
				", \"status\":\"" + status + "\"" +
				'}';
	}
}
