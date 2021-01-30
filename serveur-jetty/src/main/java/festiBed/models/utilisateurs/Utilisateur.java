package festiBed.models.utilisateurs;

import festiBed.models.statut.UtilisateurStatut;

public class Utilisateur {
    
	private int id_user;
	private String token_firebase;
	private String token_session;
	private String nom;
	private String prenom;
	private String type;
	private UtilisateurStatut statut;
	private String mail;

	public Utilisateur() {
		this.token_firebase = "not null";
		this.token_session = "not null";
		this.nom = "not null";
		this.prenom = "not null";
		this.type = UserTypes.Client.getValue();
		this.statut = UtilisateurStatut.Actif;
		this.mail = "not null";
	}

	/**
	 *
	 * @param id_user
	 * @param token_firebase
	 * @param token_session
	 * @param nom
	 * @param prenom
	 * @param type
	 * @param statut
	 * @param mail
	 */
	public Utilisateur(int id_user, String token_firebase, String token_session, String nom, String prenom, String type, UtilisateurStatut statut, String mail) {
		this.id_user = id_user;
		this.token_firebase = token_firebase;
		this.token_session = token_session;
		this.nom = nom;
		this.prenom = prenom;
		this.type = type;
		this.statut = statut;
		this.mail = mail;
	}

	public int getId_user() {
		return id_user;
	}


	public void setId_user(int id_user) {
		this.id_user = id_user;
	}


	public String getToken_firebase() {
		return token_firebase;
	}


	public void setToken_firebase(String token_firebase) {
		this.token_firebase = token_firebase;
	}


	public String getToken_session() {
		return token_session;
	}


	public void setToken_session(String token_session) {
		this.token_session = token_session;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UtilisateurStatut getStatut() {
		return statut;
	}

	public void setStatut(UtilisateurStatut statut) {
		this.statut = statut;
	}

	@Override
	public String toString() {
		return "{" +
				" \"id_user\":\"" + id_user + "\"" +
				", \"token_firebase\":\"" + token_firebase + "\"" +
				", \"token_session\":\"" + token_session + "\"" +
				", \"nom\":\"" + nom + "\"" +
				", \"prenom\":\"" + prenom  + "\"" +
				", \"type\":\"" + type + "\"" +
				", \"statut\":\"" + statut + "\"" +
				", \"mail\":\"" + mail + "\"" +
				'}';
	}
}
