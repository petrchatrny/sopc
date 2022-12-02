package cz.petrchatrny.sopc.entity.item.element;

import cz.petrchatrny.sopc.entity.item.Item;
import cz.petrchatrny.sopc.entity.item.ItemStruct;
import cz.petrchatrny.sopc.entity.item.ItemType;
import cz.petrchatrny.sopc.entity.item.OperationNotAllowedException;


import java.util.Collection;

public abstract class Element extends Item {
    protected Element(String name, String image, int count, ItemType type) {
        super(name, image, count, type, 2);
    }

    @Override
    public Collection<ItemStruct> requiredResources() throws OperationNotAllowedException {
        throw new OperationNotAllowedException("requiredResources");
    }

    @Override
    public Collection<ItemStruct> process() throws OperationNotAllowedException {
        throw new OperationNotAllowedException("process");
    }
}
