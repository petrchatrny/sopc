package cz.petrchatrny.sopc.controller;

import cz.petrchatrny.sopc.entity.agent.Inventory;
import cz.petrchatrny.sopc.entity.item.ItemType;
import cz.petrchatrny.sopc.view.ItemSquareViewHolder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class DiceDialogController implements Initializable {
    @FXML
    private FontIcon diceIC;
    @FXML
    private HBox minedOres;
    @FXML
    private Button closeBT;

    private DiceRollListener onDiceRollListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        closeBT.setOnAction(this::closeStage);
        closeBT.setDisable(true);
        roll();
    }

    public void roll() {
        Random rng = new Random();
        AtomicInteger number = new AtomicInteger();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    number.set(rng.nextInt(1, 6));
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                // update ProgressIndicator on FX thread
                Platform.runLater(() -> {
                    diceIC.setIconLiteral(numberToDiceIcon(number.get()));
                    diceIC.setRotate(diceIC.getRotate() + 45);
                });
            }
            Platform.runLater(() -> {
                diceIC.setRotate(0);
                closeBT.setDisable(false);
                onDiceRollListener.onDiceRolled(number.get());
            });
        }).start();
    }

    public void showAcquiredItems(HashMap<ItemType, Integer> localPlayerItems) {
        for (Map.Entry<ItemType, Integer> keyValue : localPlayerItems.entrySet()) {
            minedOres.getChildren().add(
                    new ItemSquareViewHolder(Inventory.getItemByItemType(keyValue.getKey(), keyValue.getValue()))
            );
        }
    }

    @FXML
    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setOnDiceRollListener(DiceRollListener onDiceRollListener) {
        this.onDiceRollListener = onDiceRollListener;
    }

    private static String numberToDiceIcon(int number) {
        return switch (number) {
            case 1 -> "fas-dice-one";
            case 2 -> "fas-dice-two";
            case 3 -> "fas-dice-three";
            case 4 -> "fas-dice-four";
            case 5 -> "fas-dice-five";
            case 6 -> "fas-dice-six";
            default -> "fas-dice-d6";
        };
    }
}
