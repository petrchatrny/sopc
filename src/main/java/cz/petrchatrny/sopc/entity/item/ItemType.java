package cz.petrchatrny.sopc.entity.item;

public enum ItemType {
    NONE(0),
    MAGNETITE(10),
    COAL(5),
    ICE(2),
    URANINITE(3),

    IRON(0),
    CARBON(0),
    SULFUR(0),
    OXYGEN(0),
    HYDROGEN(0),
    URANIUM(0),
    DARK_MATTER(0),

    STEEL(0),
    FERTILIZER(0);

    private final int DEFAULT_COUNT;

    ItemType(int DEFAULT_COUNT) {
        this.DEFAULT_COUNT = DEFAULT_COUNT;
    }

    public int getDEFAULT_COUNT() {
        return DEFAULT_COUNT;
    }
}
