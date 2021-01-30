package festiBed.models.statut;

public enum ResaCampingStatut {
    Actif("Actif"),
    Annule_Temps("Annule_Temps"),
    Annule_Client("Annule_Client"),
    Payer("Payer"),
    Traitement("Traitement"),
    Rembourser("Rembourser");

    private String value;

    ResaCampingStatut(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
