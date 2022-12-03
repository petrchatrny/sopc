package cz.petrchatrny.sopc.model;

import cz.petrchatrny.sopc.entity.item.Item;
import cz.petrchatrny.sopc.entity.item.ItemStruct;
import cz.petrchatrny.sopc.entity.item.ItemType;
import cz.petrchatrny.sopc.entity.item.OperationNotAllowedException;
import cz.petrchatrny.sopc.entity.item.element.*;
import cz.petrchatrny.sopc.entity.item.ore.*;
import cz.petrchatrny.sopc.entity.item.product.Fertilizer;
import cz.petrchatrny.sopc.entity.item.product.Steel;
import cz.petrchatrny.sopc.entity.map.StructureType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.Collection;

public class GameModel {
    private final ObservableMap<ItemType, Item> inventory;

    public GameModel() {
        this.inventory = FXCollections.observableHashMap();

        // init inventory
        for (ItemType type : ItemType.values()) {
            inventory.put(type, getDefaultItemByType(type));
        }
    }

    private Item getDefaultItemByType(ItemType type) {
        return switch (type) {
            case MAGNETITE -> new Magnetite(10);
            case COAL -> new Coal(5);
            case ICE -> new Ice(2);
            case URANINITE -> new Uraninite(3);
            case IRON -> new Iron(0);
            case CARBON -> new Carbon(0);
            case SULFUR -> new Sulfur(0);
            case OXYGEN -> new Oxygen(0);
            case HYDROGEN -> new Hydrogen(0);
            case URANIUM -> new Uranium(0);
            case DARK_MATTER -> new DarkMatter(0);
            case STEEL -> new Steel(0);
            case FERTILIZER -> new Fertilizer(0);
        };
    }

    public ObservableMap<ItemType, Item> getInventory() {
        return inventory;
    }

    /**
     * Methods access item in inventory and tries to perform process operation. If items from processing are obtained,
     * it adds them into inventory.
     *
     * @param type item type
     * @throws OperationNotAllowedException when processed item is not instance of Ore
     * @see Ore
     */
    public void processOre(ItemType type) throws OperationNotAllowedException {
        Item ore = inventory.get(type);
        if (ore.getCount() <= 0) {
            return;
        }

        ore.setCount(ore.getCount() - 1);
        Collection<ItemStruct> results = ore.process();
        for (ItemStruct res : results) {
            inventory.get(res.type()).setCount(inventory.get(res.type()).getCount() + res.count());
        }
    }

    /**
     * Method access item in inventory and tries to create it. If all goes well, required items for creating this one
     * will be deducted from inventory
     *
     * @param type item to create
     * @throws OperationNotAllowedException when created item cannot be crafted
     */
    public void craftProduct(ItemType type) throws OperationNotAllowedException {
        Item product = inventory.get(type);
        Collection<ItemStruct> requiredResources = product.requiredResources();

        boolean canCraft = true;
        for (ItemStruct requiredResource : requiredResources) {
            if (inventory.get(requiredResource.type()).getCount() < requiredResource.count()) {
                canCraft = false;
                break;
            }
        }

        if (canCraft) {
            requiredResources.forEach(requiredResource -> {
                Item resource = inventory.get(requiredResource.type());
                resource.setCount(resource.getCount() - requiredResource.count());
            });
            product.setCount(product.getCount() + 1);
        }
    }

    private void buildStructure(StructureType type) {
        // TODO implement me
    }

    private void buyGroxBox() {
        // TODO implement me
    }
}