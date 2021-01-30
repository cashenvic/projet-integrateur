package festiBed.models.statut;

public enum SoireeStatut {
    Complet("Complet"),
    Disponible("Disponible"),
    Annuler("Annuler");

    private String value;

    SoireeStatut(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }
}
