package model;

public enum AccountType {
    USER("User"),
    WORKER("Worker"),
    MANAGER("Manager"),
    ADMIN("Admin");

    private final String type;

    AccountType (String s) {
        type = s;
    }

    /**
     * returns string representation of AccountType
     * @return string representation of AccountType
     */
    public String toString() {
        return type;
    }
}
