package cz.petrchatrny.sopc.entity.item.ore;

import cz.petrchatrny.sopc.entity.item.ItemStruct;
import cz.petrchatrny.sopc.entity.item.ItemType;

import java.util.Collection;
import java.util.List;

public class Magnetite extends Ore {
    public Magnetite(int count) {
        super("Magnetit", "magnetite.png", count, ItemType.MAGNETITE);
    }

    @Override
    public Collection<ItemStruct> process() {
        return List.of(
                new ItemStruct(ItemType.IRON, 1)
        );
    }
}
