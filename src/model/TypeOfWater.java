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

    public String toString() {
        return type;
    }
}
