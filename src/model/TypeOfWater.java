package model;

public enum TypeOfWater {
    Bottled("Bottled"),
    Well("Well"),
    Stream("Stream"),
    Lake("Lake"),
    Spring("Spring"),
    Other("Other");

    private final String type;

    TypeOfWater (String s) {
        type = s;
    }

    /**
     * returns string representation of TypeOfWater
     * @return string representation of TypeOfWater
     */
    public String toString() {
        return type;
    }
}
