package model;

public enum OverallCondition {
    Safe("Safe"),
    Treatable("Treatable"),
    Unsafe("Unsafe");

    private final String type;

    OverallCondition (String s) {
        type = s;
    }

    public String toString() {
        return type;
    }
}
