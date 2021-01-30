package festiBed.models.utilisateurs;

public enum UserTypes {
    Organisateur("Organisateur"),
    Hebergeur("Hebergeur"),
    Client("Client");

    private String value;

    UserTypes(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }
}
