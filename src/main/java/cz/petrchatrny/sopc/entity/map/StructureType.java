package cz.petrchatrny.sopc.entity.map;

import cz.petrchatrny.sopc.entity.item.ItemStruct;
import cz.petrchatrny.sopc.entity.item.ItemType;

import java.util.Collection;
import java.util.List;

public enum StructureType {
    SPACESHIP(List.of(
            new ItemStruct(ItemType.STEEL, 1),
            new ItemStruct(ItemType.HYDROGEN, 2),
            new ItemStruct(ItemType.OXYGEN, 2))
    ),
    SPACE_STATION(List.of(
            new ItemStruct(ItemType.STEEL, 3),
            new ItemStruct(ItemType.URANIUM, 2),
            new ItemStruct(ItemType.FERTILIZER, 2),
            new ItemStruct(ItemType.OXYGEN, 2))
    ),
    WORMHOLE(List.of(
            new ItemStruct(ItemType.URANIUM, 2),
            new ItemStruct(ItemType.SULFUR, 4),
            new ItemStruct(ItemType.DARK_MATTER, 1))
    );

    private Collection<ItemStruct> requiredResources;

    StructureType(Collection<ItemStruct> requiredResources) {
        this.requiredResources = requiredResources;
    }


    public Collection<ItemStruct> getRequiredResources() {
        return requiredResources;
    }
}