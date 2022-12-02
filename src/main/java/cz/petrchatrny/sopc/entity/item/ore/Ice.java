package cz.petrchatrny.sopc.entity.item.ore;

import cz.petrchatrny.sopc.entity.item.ItemStruct;
import cz.petrchatrny.sopc.entity.item.ItemType;

import java.util.Collection;
import java.util.List;

public class Ice extends Ore {
    public Ice(int count) {
        super("Led", "ice.png", count, ItemType.ICE);
    }

    @Override
    public Collection<ItemStruct> process() {
        return List.of(
                new ItemStruct(ItemType.HYDROGEN, 2),
                new ItemStruct(ItemType.OXYGEN, 1)
        );
    }
}
