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

import java.util.Arrays;

public class Inventory {
    private final ObservableMap<ItemType, Item> items;

    public Inventory() {
        this.items = FXCollections.observableHashMap();

        Arrays.stream(ItemType.values())
                .filter(itemType -> itemType != ItemType.NONE)
                .forEach(itemType -> items.put(itemType, getDefaultItemByType(itemType)));
    }

    public static Item getItemByItemType(ItemType type, int count) {
        return switch(type) {
            case MAGNETITE -> new Magnetite(count);
            case COAL -> new Coal(count);
            case ICE -> new Ice(count);
            case URANINITE -> new Uraninite(count);
            case IRON -> new Iron(count);
            case CARBON -> new Carbon(count);
            case SULFUR -> new Sulfur(count);
            case OXYGEN -> new Oxygen(count);
            case HYDROGEN -> new Hydrogen(count);
            case URANIUM -> new Uranium(count);
            case DARK_MATTER -> new DarkMatter(count);
            case STEEL -> new Steel(count);
            case FERTILIZER -> new Fertilizer(count);
            case NONE -> null;
        };
    }

    private Item getDefaultItemByType(ItemType type) {
        return getItemByItemType(type, type.getDEFAULT_COUNT());
    }

    public ObservableMap<ItemType, Item> getItems() {
        return items;
    }
}
