package cz.petrchatrny.sopc.entity.item.product;


import cz.petrchatrny.sopc.entity.item.Item;
import cz.petrchatrny.sopc.entity.item.ItemStruct;
import cz.petrchatrny.sopc.entity.item.ItemType;
import cz.petrchatrny.sopc.entity.item.OperationNotAllowedException;

import java.util.Collection;

public abstract class Product extends Item {
    protected Product(String name, String image, int count, ItemType type) {
        super(name, image, count, type, 3);
    }

    @Override
    public Collection<ItemStruct> process() throws OperationNotAllowedException {
        throw new OperationNotAllowedException("process");
    }
}
