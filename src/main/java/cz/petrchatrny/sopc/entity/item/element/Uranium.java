package cz.petrchatrny.sopc.entity.item.element;

import cz.petrchatrny.sopc.entity.item.ItemType;


public class Uranium extends Element {
    public Uranium(int count) {
        super("Uran", "uranium.png", count, ItemType.URANIUM);
    }
}
