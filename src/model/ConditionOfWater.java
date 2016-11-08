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

    /**
     * returns string representation of ConditionOfWater
     * @return string representation of ConditionOfWater
     */
    public String toString() {
        return type;
    }

}
