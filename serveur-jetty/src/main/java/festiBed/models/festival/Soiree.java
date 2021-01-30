package festiBed.models.festival;

import festiBed.models.statut.SoireeStatut;

import java.util.Date;
import java.util.List;

public class Soiree {
	private int id_soiree;
	private Date date_soiree;
	private SoireeStatut status;
	private int id_festival;

	private Festival festival;
	private List<CategorieSoiree> categorie_soiree;

	public Soiree() {}

	public Soiree(int id_soiree, Date date_soiree, SoireeStatut status, int id_festival) {
		this.id_soiree = id_soiree;
		this.date_soiree = date_soiree;
		this.status = status;
		this.id_festival = id_festival;
	}

	public int getId_soiree() {
		return id_soiree;
	}

	public void setId_soiree(int id_soiree) {
		this.id_soiree = id_soiree;
	}

	public Date getDate_soiree() {
		return date_soiree;
	}

	public void setDate_soiree(Date date_soiree) {
		this.date_soiree = date_soiree;
	}

	public SoireeStatut getStatus() {
		return status;
	}

	public void setStatus(SoireeStatut status) {
		this.status = status;
	}

	public Festival getFestivals() {
		return festival;
	}

	public void setFestivals(Festival festivals) {
		this.festival = festivals;
	}

	public List<CategorieSoiree> getCategorie_soiree() {
		return categorie_soiree;
	}

	public void setCategorie_soiree(List<CategorieSoiree> categorie_soiree) {
		this.categorie_soiree = categorie_soiree;
	}

	public int getId_festival() {
		return id_festival;
	}

	public void setId_festival(int id_festival) {
		this.id_festival = id_festival;
	}

	@Override
	public String toString() {
		return "{" +
				"\"id_soiree\":\"" + id_soiree + "\"" +
				", \"date_soiree\":\"" + date_soiree + "\"" +
				", \"status\":\"" + status + "\"" +
				", \"id_festival\":\"" + id_festival + "\"" +
				'}';
	}
}
