package festiBed.models.festival;

import festiBed.utils.Status;

public class PhotoFestival {
	private Long id_photo;
	private String chemin;
	
	private String commentaire;
	private Status status;
	private Festival festival;
	
	public PhotoFestival() {}

	public PhotoFestival(Long id_photo, String chemin, String commentaire, Status status, Festival festivals) {
		super();
		this.id_photo = id_photo;
		this.chemin = chemin;
		this.commentaire = commentaire;
		this.status = status;
		this.festival = festivals;
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

	public Festival getFestivals() {
		return festival;
	}

	public void setFestivals(Festival festivals) {
		this.festival = festivals;
	}

}
