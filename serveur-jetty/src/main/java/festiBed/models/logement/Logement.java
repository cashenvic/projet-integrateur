package festiBed.models.logement;

import java.util.Date;
import java.util.List;

import festiBed.models.hebergement.Hebergement;
import festiBed.models.panier.Panier;
import festiBed.models.panier.ReservationLogement;
import festiBed.utils.Status;

public abstract class Logement {
	private Long id_user;

	private Status status;

	private double tarif;
	
	private Date date_creation;
	private Hebergement hebergement;
	private List< ReservationLogement > resaLogements;
	
    private List<Panier> paniers;
	
	public Logement() {}

	public Logement(Long id_user, Status status, double tarif, Date date_creation,
			Hebergement hebergement, List<ReservationLogement> resaLogements, List<Panier> paniers) {
		super();
		this.id_user = id_user;
		this.status = status;
		this.tarif = tarif;
		this.date_creation = date_creation;
		this.hebergement = hebergement;
		this.resaLogements = resaLogements;
		this.paniers = paniers;
	}

	public Long getId_user() {
		return id_user;
	}

	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public double getTarif() {
		return tarif;
	}

	public void setTarif(double tarif) {
		this.tarif = tarif;
	}

	public Date getDate_creation() {
		return date_creation;
	}

	public void setDate_creation(Date date_creation) {
		this.date_creation = date_creation;
	}

	public Hebergement getHebergement() {
		return hebergement;
	}

	public void setHebergement(Hebergement hebergement) {
		this.hebergement = hebergement;
	}

	public List<ReservationLogement> getResaLogements() {
		return resaLogements;
	}

	public void setResaLogements(List<ReservationLogement> resaLogements) {
		this.resaLogements = resaLogements;
	}

	public List<Panier> getPaniers() {
		return paniers;
	}

	public void setPaniers(List<Panier> paniers) {
		this.paniers = paniers;
	}

}
