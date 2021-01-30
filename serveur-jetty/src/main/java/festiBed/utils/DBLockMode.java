package festiBed.utils;

public enum DBLockMode {
    EXCLUSIVE("exclusive"),
    SHARE("share");

    private String value;

    DBLockMode (String v) {
        value = v;
    }

    public String getValue() {
        return this.value;
    }
}
