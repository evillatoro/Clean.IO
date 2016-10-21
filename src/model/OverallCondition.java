package model;

public enum OverallCondition {
    Safe("Safe"),
    Treatable("Treatable"),
    Unsafe("Unsafe");

    private final String type;

    OverallCondition (String s) {
        type = s;
    }

    /**
     * returns string representation of OverallCondition
     * @return string representation of OverallCondition
     */
    public String toString() {
        return type;
    }
}
