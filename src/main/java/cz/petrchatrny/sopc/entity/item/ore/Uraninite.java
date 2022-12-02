package cz.petrchatrny.sopc.entity.item.ore;

import cz.petrchatrny.sopc.entity.item.ItemStruct;
import cz.petrchatrny.sopc.entity.item.ItemType;

import java.util.Collection;
import java.util.List;

public class Uraninite extends Ore {
    public Uraninite(int count) {
        super("Smolinec", "uraninite.png", count, ItemType.URANINITE);
    }

    @Override
    public Collection<ItemStruct> process() {
        return List.of(
                new ItemStruct(ItemType.URANIUM, 1)
        );
    }
}
