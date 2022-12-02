package cz.petrchatrny.sopc.model;

import cz.petrchatrny.sopc.entity.item.Item;
import cz.petrchatrny.sopc.entity.item.ItemType;
import cz.petrchatrny.sopc.entity.item.element.*;
import cz.petrchatrny.sopc.entity.item.ore.Coal;
import cz.petrchatrny.sopc.entity.item.ore.Ice;
import cz.petrchatrny.sopc.entity.item.ore.Magnetite;
import cz.petrchatrny.sopc.entity.item.ore.Uraninite;
import cz.petrchatrny.sopc.entity.item.product.Fertilizer;
import cz.petrchatrny.sopc.entity.item.product.Steel;
import cz.petrchatrny.sopc.entity.map.StructureType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

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


    private void craftProduct(ItemType type) {
        // TODO implement me
    }

    private void buildStructure(StructureType type) {
        // TODO implement me
    }

    private void buyGroxBox() {
        // TODO implement me
    }
}