package cz.petrchatrny.sopc.entity.item.product;

import cz.petrchatrny.sopc.entity.item.ItemStruct;
import cz.petrchatrny.sopc.entity.item.ItemType;

import java.util.Collection;
import java.util.List;

public class Steel extends Product {
    public Steel(int count) {
        super("Ocel", "steel.png", count, ItemType.STEEL);
    }

    @Override
    public Collection<ItemStruct> requiredResources() {
        return List.of(
                new ItemStruct(ItemType.CARBON, 1),
                new ItemStruct(ItemType.IRON, 1)
        );
    }
}
