package model;

public enum ConditionOfWater {
    Waste("Waste"),
    Treatable_Clear("Treatable-Clear"),
    Treatable_Muddy("Treatable-Muddy"),
    Potable("Potable");

    private final String type;

    ConditionOfWater (String s) {
        type = s;
    }

    public String toString() {
        return type;
    }
}
