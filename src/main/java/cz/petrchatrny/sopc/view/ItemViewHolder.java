package cz.petrchatrny.sopc.view;

import cz.petrchatrny.sopc.App;
import cz.petrchatrny.sopc.entity.item.Item;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ItemViewHolder extends ListCell<Item> {
    public HBox view;
    public ImageView image;
    public Label name;
    public Label count;

    public ItemViewHolder(Item item) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view-holder/item.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        name.setText(item.getName());
        count.setText(item.getCount() + " ks");
        if (!item.getImage().equals("")) {
            URL url = App.class.getResource(item.getImage());
            image.setImage(new Image(Objects.requireNonNull(url).toString()));
        }
    }


    public HBox getView() {
        return view;
    }
}
