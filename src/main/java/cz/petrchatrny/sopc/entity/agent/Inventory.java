package cz.petrchatrny.sopc.entity.agent;

import cz.petrchatrny.sopc.entity.item.Item;
import cz.petrchatrny.sopc.entity.item.ItemType;
import cz.petrchatrny.sopc.entity.item.element.*;
import cz.petrchatrny.sopc.entity.item.ore.Coal;
import cz.petrchatrny.sopc.entity.item.ore.Ice;
import cz.petrchatrny.sopc.entity.item.ore.Magnetite;
import cz.petrchatrny.sopc.entity.item.ore.Uraninite;
import cz.petrchatrny.sopc.entity.item.product.Fertilizer;
import cz.petrchatrny.sopc.entity.item.product.Steel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Inventory {
    private final ObservableMap<ItemType, Item> items;

    public Inventory() {
        this.items = FXCollections.observableHashMap();

        Arrays.stream(ItemType.values())
                .filter(itemType -> itemType != ItemType.NONE)
                .forEach(itemType -> items.put(itemType, getDefaultItemByType(itemType)));
    }

    private Item getDefaultItemByType(ItemType type) {
        return switch (type) {
            case MAGNETITE -> new Magnetite(type.getDEFAULT_COUNT());
            case COAL -> new Coal(type.getDEFAULT_COUNT());
            case ICE -> new Ice(type.getDEFAULT_COUNT());
            case URANINITE -> new Uraninite(type.getDEFAULT_COUNT());
            case IRON -> new Iron(type.getDEFAULT_COUNT());
            case CARBON -> new Carbon(type.getDEFAULT_COUNT());
            case SULFUR -> new Sulfur(type.getDEFAULT_COUNT());
            case OXYGEN -> new Oxygen(type.getDEFAULT_COUNT());
            case HYDROGEN -> new Hydrogen(type.getDEFAULT_COUNT());
            case URANIUM -> new Uranium(type.getDEFAULT_COUNT());
            case DARK_MATTER -> new DarkMatter(type.getDEFAULT_COUNT());
            case STEEL -> new Steel(type.getDEFAULT_COUNT());
            case FERTILIZER -> new Fertilizer(type.getDEFAULT_COUNT());
        };
    }

    public ObservableMap<ItemType, Item> getItems() {
        return items;
    }
}
