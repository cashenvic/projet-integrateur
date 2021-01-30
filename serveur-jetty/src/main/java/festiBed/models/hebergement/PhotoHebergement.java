package festiBed.models.hebergement;

import festiBed.utils.Status;

public class PhotoHebergement {
	private Long id_photo;
	
	private String chemin;
	
	private String commentaire;
	private Status status;

	private Hebergement hebergement;
	
	public PhotoHebergement() {}

	public PhotoHebergement(Long id_photo, String chemin, String commentaire, Status status,
			Hebergement hebergement) {
		super();
		this.id_photo = id_photo;
		this.chemin = chemin;
		this.commentaire = commentaire;
		this.status = status;
		this.hebergement = hebergement;
	}

	public Long getId_photo() {
		return id_photo;
	}

	public void setId_photo(Long id_photo) {
		this.id_photo = id_photo;
	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Hebergement getHebergement() {
		return hebergement;
	}

	public void setHebergement(Hebergement hebergement) {
		this.hebergement = hebergement;
	}
	
}
