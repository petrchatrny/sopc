package cz.petrchatrny.sopc.entity.item.element;

import cz.petrchatrny.sopc.entity.item.ItemType;


public class Iron extends Element {
    public Iron(int count) {
        super("Železo", "iron.png", count, ItemType.IRON);
    }
}
