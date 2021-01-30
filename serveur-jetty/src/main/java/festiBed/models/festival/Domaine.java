package festiBed.models.festival;

import java.util.ArrayList;
import java.util.List;

public class Domaine {
	private Long id_domaine;
	private String nom;
	private List<Festival> festivals = new ArrayList<>();
	
	
	public Domaine() {
	}

	public Domaine(Long id_domaine, String nom, List<Festival> festivals) {
		super();
		this.id_domaine = id_domaine;
		this.nom = nom;
		this.festivals = festivals;
	}

	public Long getId_domaine() {
		return id_domaine;
	}

	public void setId_domaine(Long id_domaine) {
		this.id_domaine = id_domaine;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Festival> getFestivals() {
		return festivals;
	}

	public void setFestivals(List<Festival> festivals) {
		this.festivals = festivals;
	}

}
