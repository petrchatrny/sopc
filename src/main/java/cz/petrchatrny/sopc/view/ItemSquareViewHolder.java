package cz.petrchatrny.sopc.view;

import cz.petrchatrny.sopc.App;
import cz.petrchatrny.sopc.entity.item.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ItemSquareViewHolder extends AnchorPane {
    @FXML public Node item;
    @FXML public ImageView image;
    @FXML public Label count;

    public ItemSquareViewHolder(Item item) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view-holder/item-square.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            count.setText((String.valueOf(item.getCount())));
            if (!item.getImage().equals("")) {
                URL url = App.class.getResource(item.getImage());
                image.setImage(new Image(Objects.requireNonNull(url).toString()));
            }

            getChildren().add(this.item);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
