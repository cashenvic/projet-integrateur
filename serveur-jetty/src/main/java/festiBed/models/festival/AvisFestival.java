package festiBed.models.festival;

import festiBed.utils.Status;
import festiBed.models.*;

public class AvisFestival {
	private Long id_avis;
	private double note;
	
	private String commentaire;
	private Status statut;
	private Festival festival;
	
	
	public AvisFestival() {
	}


	public AvisFestival(Long id_avis, double note, String commentaire, Status statut, Festival festivals) {
		super();
		this.id_avis = id_avis;
		this.note = note;
		this.commentaire = commentaire;
		this.statut = statut;
		this.festival = festivals;
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


	public Festival getFestivals() {
		return festival;
	}


	public void setFestivals(Festival festivals) {
		this.festival = festivals;
	}

}
