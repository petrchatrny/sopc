package cz.petrchatrny.sopc.entity.item.ore;

import cz.petrchatrny.sopc.entity.item.Item;
import cz.petrchatrny.sopc.entity.item.ItemStruct;
import cz.petrchatrny.sopc.entity.item.ItemType;
import cz.petrchatrny.sopc.entity.item.OperationNotAllowedException;


import java.util.Collection;

public abstract class Ore extends Item {
    protected Ore(String name, String image, int count, ItemType type) {
        super(name, image, count, type, 1);
    }

    @Override
    public Collection<ItemStruct> requiredResources() throws OperationNotAllowedException {
        throw new OperationNotAllowedException("requiredResources");
    }
}
