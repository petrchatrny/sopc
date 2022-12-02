package cz.petrchatrny.sopc.entity.item.ore;

import cz.petrchatrny.sopc.entity.item.ItemStruct;
import cz.petrchatrny.sopc.entity.item.ItemType;

import java.util.Collection;
import java.util.List;

public class Coal extends Ore {
    public Coal(int count) {
        super("Uhlí", "coal.png", count, ItemType.COAL);
    }

    @Override
    public Collection<ItemStruct> process() {
        return List.of(
                new ItemStruct(ItemType.CARBON, 2),
                new ItemStruct(ItemType.SULFUR, 1)
        );
    }
}
