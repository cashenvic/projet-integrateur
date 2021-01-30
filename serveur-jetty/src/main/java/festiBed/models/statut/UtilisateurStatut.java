package festiBed.models.statut;

public enum UtilisateurStatut {
    Actif("Actif"),
    Inactif("Inactif");

    private String value;

    UtilisateurStatut(String v) {
        this.value = v;
    }

    public String getValue() {
        return this.value;
    }
}
