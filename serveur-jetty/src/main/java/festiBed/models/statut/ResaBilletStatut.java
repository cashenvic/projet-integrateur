package festiBed.models.statut;

public enum ResaBilletStatut {
    Actif("Actif"),
    Annule_Temps("Annule_Temps"),
    Annule_Client("Annule_Client"),
    Payer("Payer"),
    Traitement("Traitement"),
    Rembourser("Rembourser");


    private String value;

    ResaBilletStatut(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
