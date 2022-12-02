package cz.petrchatrny.sopc.entity.item.element;

import cz.petrchatrny.sopc.entity.item.ItemType;

public class Carbon extends Element {
    public Carbon(int count) {
        super("Uhlík", "carbon.png", count, ItemType.CARBON);
    }
}
