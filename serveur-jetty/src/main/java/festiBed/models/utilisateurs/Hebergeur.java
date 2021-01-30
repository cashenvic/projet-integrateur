package festiBed.models.utilisateurs;

import java.util.ArrayList;
import java.util.List;

import festiBed.models.hebergement.Hebergement;

public class Hebergeur extends Utilisateur {
	
	private List<Hebergement> hebergements = new ArrayList<>();
}
