package cz.petrchatrny.sopc.entity.item.element;

import cz.petrchatrny.sopc.entity.item.ItemType;

public class Oxygen extends Element {
    public Oxygen(int count) {
        super("Kyslík", "oxygen.png", count, ItemType.OXYGEN);
    }
}
