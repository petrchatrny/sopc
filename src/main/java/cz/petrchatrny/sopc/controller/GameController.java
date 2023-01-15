package cz.petrchatrny.sopc.controller;

import cz.petrchatrny.sopc.entity.item.Item;
import cz.petrchatrny.sopc.entity.item.ItemType;
import cz.petrchatrny.sopc.entity.item.OperationNotAllowedException;
import cz.petrchatrny.sopc.entity.map.MapStructureClickListener;
import cz.petrchatrny.sopc.entity.map.StructureType;
import cz.petrchatrny.sopc.model.SinglePlayerModel;
import cz.petrchatrny.sopc.model.Settings;
import cz.petrchatrny.sopc.view.Cell;
import cz.petrchatrny.sopc.view.Edge;
import cz.petrchatrny.sopc.view.InventoryCellFactory;
import cz.petrchatrny.sopc.view.AgentViewHolder;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable, TurnChangeListener {
    @FXML private Pane controlPanel;
    @FXML private HBox playersHB;
    @FXML private ListView<Item> inventoryLV;
    @FXML private Pane map;
    @FXML private Button magnetiteBT;
    @FXML private Button coalBT;
    @FXML private Button iceBT;
    @FXML private Button uraniniteBT;
    @FXML private Button steelBT;
    @FXML private Button fertilizerBT;
    @FXML private Button groxBoxBT;
    @FXML private Button spaceStationBT;
    @FXML private Button spaceShipBT;
    @FXML private Button wormHoleBT;
    @FXML private Button diceBT;
    @FXML private Button nextTurnBT;

    private SinglePlayerModel model;

    public GameController() {
    }

    public GameController(SinglePlayerModel model) {
        this.model = model;
        this.model.setTurnChangeListener(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        magnetiteBT.setOnAction(event -> processOre(ItemType.MAGNETITE));
        coalBT.setOnAction(event -> processOre(ItemType.COAL));
        iceBT.setOnAction(event -> processOre(ItemType.ICE));
        uraniniteBT.setOnAction(event -> processOre(ItemType.URANINITE));
        steelBT.setOnAction(event -> craftProduct(ItemType.STEEL));
        fertilizerBT.setOnAction(event -> craftProduct(ItemType.FERTILIZER));
        groxBoxBT.setOnAction(event -> buyGroxBox());
        spaceShipBT.setOnAction(event -> buildStructure(StructureType.SPACESHIP));
        spaceStationBT.setOnAction(event -> buildStructure(StructureType.SPACESTATION));
        wormHoleBT.setOnAction(event -> buildStructure(StructureType.WORMHOLE));
        wormHoleBT.setDisable(true);
        diceBT.setOnAction(event -> rollDice());
        nextTurnBT.setOnAction(event -> model.nextTurn());

        // show map
        map.getChildren().add(model.getMapPlan().getCanvas());

        // disable selection of item in listview
        inventoryLV.addEventFilter(MouseEvent.MOUSE_PRESSED, Event::consume);

        // show players in top bar
        model.getAgents().forEach(player -> playersHB.getChildren().add(
                new AgentViewHolder(player, Settings.MAX_SPACESHIP_COUNT, Settings.MAX_SPACE_STATION_COUNT)
        ));

        // show items in local player's inventory
        ArrayList<Item> items = new ArrayList<>(model.getLocalPlayerInventory().values());
        inventoryLV.setCellFactory(new InventoryCellFactory());
        inventoryLV.setItems(FXCollections.observableList(items).sorted());
    }

    private void processOre(ItemType type) {
        try {
            model.processOre(type);
            inventoryLV.refresh();
        } catch (OperationNotAllowedException e) {
            showInvalidOperationError();
        }
    }

    private void craftProduct(ItemType type) {
        try {
            model.craftProduct(type);
            inventoryLV.refresh();
        } catch (OperationNotAllowedException e) {
            showInvalidOperationError();
        }
    }

    private void buildStructure(StructureType type) {
        // TODO implement me
    }

    private void buyGroxBox() {
        // TODO implement me
    }

    private void showInvalidOperationError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chyba");
        alert.setContentText("Operace není povolena.");
        alert.showAndWait();
    }

    @Override
    public void onTurnChanged(boolean isLocalPlayerOnTurn) {
        if (isLocalPlayerOnTurn) {
            controlPanel.setVisible(true);
        } else {
            controlPanel.setVisible(false);
        }
    }
}
