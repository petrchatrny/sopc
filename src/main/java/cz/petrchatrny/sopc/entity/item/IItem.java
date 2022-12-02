package cz.petrchatrny.sopc.entity.item;

import java.util.Collection;

public interface IItem {
    Collection<ItemStruct> requiredResources() throws OperationNotAllowedException;

    Collection<ItemStruct> process() throws OperationNotAllowedException;
}
