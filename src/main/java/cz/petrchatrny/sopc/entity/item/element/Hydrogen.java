package cz.petrchatrny.sopc.entity.item.element;

import cz.petrchatrny.sopc.entity.item.ItemType;


public class Hydrogen extends Element {
    public Hydrogen(int count) {
        super("Vodík", "hydrogen.png", count, ItemType.HYDROGEN);
    }
}
