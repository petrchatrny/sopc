package cz.petrchatrny.sopc.entity.agent;

public enum Color {
    RED("FF0000"),
    GREEN("00FF00"),
    BLUE("0000FF"),
    GRAY("808080");

    private String value;

    Color(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
