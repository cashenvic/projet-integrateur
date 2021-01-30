package festiBed.models.utilisateurs;

import festiBed.models.utilisateurs.Utilisateur;

import java.util.List;

import festiBed.models.panier.Panier;

public class Client extends Utilisateur {
	
	private List<Panier> paniers;
}
