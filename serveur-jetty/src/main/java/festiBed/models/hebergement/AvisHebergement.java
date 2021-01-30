package festiBed.models.hebergement;

import festiBed.models.hebergement.Hebergement;
import festiBed.utils.Status;

public class AvisHebergement {
	private Long id_avis;
	private double note;
	
	private String commentaire;
	private Status statut;
	private Hebergement hebergement;
	
	
	public AvisHebergement() {
	}

	public AvisHebergement(Long id_avis, double note, String commentaire, Status statut,
			Hebergement hebergement) {
		super();
		this.id_avis = id_avis;
		this.note = note;
		this.commentaire = commentaire;
		this.statut = statut;
		this.hebergement = hebergement;
	}


	public Long getId_avis() {
		return id_avis;
	}


	public void setId_avis(Long id_avis) {
		this.id_avis = id_avis;
	}


	public double getNote() {
		return note;
	}


	public void setNote(double note) {
		this.note = note;
	}


	public String getCommentaire() {
		return commentaire;
	}


	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}


	public Status getStatut() {
		return statut;
	}


	public void setStatut(Status statut) {
		this.statut = statut;
	}


	public Hebergement getHebergement() {
		return hebergement;
	}


	public void setHebergement(Hebergement hebergement) {
		this.hebergement = hebergement;
	}

}
