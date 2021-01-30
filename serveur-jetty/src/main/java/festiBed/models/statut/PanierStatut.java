package festiBed.models.statut;

public enum PanierStatut {
    Traitement("Traitement"),
    Annule_Client("Annule_Client"),
    Payer("Payer"),
    Rembourser("Rembourser");

    private String value;

    PanierStatut(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }
}
