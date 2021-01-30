package festiBed.models.logement;

public abstract class Chambre extends Logement{
	private int nbr_place_adulte;
	private int nbr_place_enfant;
	private double tarif_adulte;
	private double tarif_enfant;

	public int getNbr_place_adulte() {
		return nbr_place_adulte;
	}

	public void setNbr_place_adulte(int nbr_place_adulte) {
		this.nbr_place_adulte = nbr_place_adulte;
	}

	public int getNbr_place_enfant() {
		return nbr_place_enfant;
	}

	public void setNbr_place_enfant(int nbr_place_enfant) {
		this.nbr_place_enfant = nbr_place_enfant;
	}

	public double getTarif_adulte() {
		return tarif_adulte;
	}

	public void setTarif_adulte(double tarif_adulte) {
		this.tarif_adulte = tarif_adulte;
	}

	public double getTarif_enfant() {
		return tarif_enfant;
	}

	public void setTarif_enfant(double tarif_enfant) {
		this.tarif_enfant = tarif_enfant;
	}

}
