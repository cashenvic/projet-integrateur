package festiBed.models.statut;

public enum HebergementStatut {
    Actif("Actif"),
    Inactif("Inactif"),
    Annuler("Annuler"),;

    private String value;

    HebergementStatut(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }
}
