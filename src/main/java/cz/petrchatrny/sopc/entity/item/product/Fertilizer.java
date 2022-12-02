package cz.petrchatrny.sopc.entity.item.product;

import cz.petrchatrny.sopc.entity.item.ItemStruct;
import cz.petrchatrny.sopc.entity.item.ItemType;

import java.util.Collection;
import java.util.List;

public class Fertilizer extends Product {
    public Fertilizer(int count) {
        super("Hnojivo", "fertilizer.png", count, ItemType.FERTILIZER);
    }

    @Override
    public Collection<ItemStruct> requiredResources() {
        return List.of(
                new ItemStruct(ItemType.CARBON, 2),
                new ItemStruct(ItemType.SULFUR, 3),
                new ItemStruct(ItemType.OXYGEN, 2)
        );
    }
}
