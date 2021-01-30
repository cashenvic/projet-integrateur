package festiBed.models.festival;

import festiBed.models.Localisation;
import festiBed.models.utilisateurs.Organisateur;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Festival {
	private int id_festival;
	private String nom;
	
	private String description;
	private Date date_debut;
	private Date date_fin;	
	private String site_web;	
	private double note_globale;	
	private Date date_creation;
	private String status;
	private double latitude;
	private double longitude;

	private int id_user;
	
	private List<Domaine> domaines = new ArrayList<>();
	private List<AvisFestival> avis = new ArrayList<>();

	private List<PhotoFestival> photos = new ArrayList<>();
	
	private List<Soiree> soirees = new ArrayList<>();

	private Organisateur organisateur;
	
	private Localisation localisation;
	
	public Festival() {
		
	}

	public Festival(int id_festival, String nom, String description, Date date_debut,
			Date date_fin, String site_web, double note_globale, Date date_creation, String status,
			List<Domaine> domaines, List<AvisFestival> avisNotes, List<PhotoFestival> photos, List<Soiree> soirees,
			 Organisateur organisateur, Localisation localisation) {
		super();
		this.id_festival = id_festival;
		this.nom = nom;
		this.description = description;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.site_web = site_web;
		this.note_globale = note_globale;
		this.date_creation = date_creation;
		this.status = status;
		this.domaines = domaines;
		this.avis = avisNotes;
		this.photos = photos;
		this.soirees = soirees;
		this.organisateur = organisateur;
		this.localisation = localisation;
	}

	public Festival(int id_festival, String nom, String description, Date date_debut,
			Date date_fin, String site_web, double note_globale, Date date_creation, String status,
			double longitude, double latitude) {
		super();
		this.id_festival = id_festival;
		this.nom = nom;
		this.description = description;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.site_web = site_web;
		this.note_globale = note_globale;
		this.date_creation = date_creation;
		this.status = status;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getId_festival() {
		return id_festival;
	}

	public void setId_festival(int id_festival) {
		this.id_festival = id_festival;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(Date date_debut) {
		this.date_debut = date_debut;
	}

	public Date getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(Date date_fin) {
		this.date_fin = date_fin;
	}

	public String getSite_web() {
		return site_web;
	}

	public void setSite_web(String site_web) {
		this.site_web = site_web;
	}

	public double getNote_globale() {
		return note_globale;
	}

	public void setNote_globale(double note_globale) {
		this.note_globale = note_globale;
	}

	public Date getDate_creation() {
		return date_creation;
	}

	public void setDate_creation(Date date_creation) {
		this.date_creation = date_creation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Domaine> getDomaines() {
		return domaines;
	}

	public void setDomaines(List<Domaine> domaines) {
		this.domaines = domaines;
	}

	public List<AvisFestival> getAvisNotes() {
		return avis;
	}

	public void setAvisNotes(List<AvisFestival> avisNotes) {
		this.avis = avisNotes;
	}

	public List<PhotoFestival> getPhotos() {
		return photos;
	}

	public void setPhotos(List<PhotoFestival> photos) {
		this.photos = photos;
	}

	public List<Soiree> getSoirees() {
		return soirees;
	}

	public void setSoirees(List<Soiree> soirees) {
		this.soirees = soirees;
	}

	public Organisateur getOrganisateur() {
		return organisateur;
	}

	public void setOrganisateur(Organisateur organisateur) {
		this.organisateur = organisateur;
	}

	public Localisation getLocalisation() {
		return localisation;
	}

	public void setLocalisation(Localisation localisation) {
		this.localisation = localisation;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	@Override
	public String toString() {
		return "{" +
				"\"id_festival\": " + id_festival +
				", \"nom\":\"" + nom + "\"" +
				", \"description\":\"" + description + "\"" +
				", \"date_debut\":\"" + date_debut +"\"" +
				", \"date_fin\":\"" + date_fin +"\"" +
				", \"site_web\":\"" + site_web + "\"" +
				", \"note_global\":\"" + note_globale +"\"" +
				", \"latitude\": \"" + latitude +"\""+
				", \"longitude\": \"" + longitude +"\""+
				", \"date_creation\":\"" + date_creation +"\"" +
				", \"status\":\"" + status + "\"" +
				"}";
	}
}
