package festiBed.models;

import java.util.ArrayList;
import java.util.List;

import festiBed.models.festival.Festival;
import festiBed.models.hebergement.Hebergement;

public class Localisation {
	private Float latitude;
	private Float longitude;
	private String nom_departement;
	private String code_postal;
	private List<Festival> festivals = new ArrayList<>();
	private List<Hebergement> hebergements = new ArrayList<>();
	
	public Localisation() {}
	
	public Localisation(String nom_departement, String commune, String code_postal,
			List<Festival> festivals, List<Hebergement> hebergements) {
		super();
		this.nom_departement = nom_departement;
		this.code_postal = code_postal;
		this.festivals = festivals;
		this.hebergements = hebergements;
	}

	public Localisation(Float longitude, Float latitude, String nom_departement, String code_postal){
		this.code_postal = code_postal;
		this.latitude = latitude;
		this.longitude = longitude;
		this.nom_departement = nom_departement;
	}

	public String getNom_departement() {
		return nom_departement;
	}
	
	public void setNom_departement(String nom_departement) {
		this.nom_departement = nom_departement;
	}
	
	public String getCode_postal() {
		return code_postal;
	}
	
	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	
}
