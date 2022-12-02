package cz.petrchatrny.sopc.entity.item.element;

import cz.petrchatrny.sopc.entity.item.ItemType;


public class DarkMatter extends Element {
    public DarkMatter(int count) {
        super("Temná hmota", "dark-matter.png", count, ItemType.DARK_MATTER);
    }
}
