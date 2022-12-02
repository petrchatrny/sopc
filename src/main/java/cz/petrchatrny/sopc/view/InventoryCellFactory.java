package cz.petrchatrny.sopc.view;

import cz.petrchatrny.sopc.entity.item.Item;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class InventoryCellFactory implements Callback<ListView<Item>, ListCell<Item>> {
    @Override
    public ListCell<Item> call(ListView<Item> param) {
        return new ListCell<>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    ItemViewHolder viewHolder = new ItemViewHolder(item);
                    setGraphic(viewHolder.getView());
                }
            }
        };
    }
}
