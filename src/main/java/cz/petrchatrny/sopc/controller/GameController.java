package cz.petrchatrny.sopc.controller;

import cz.petrchatrny.sopc.entity.item.Item;
import cz.petrchatrny.sopc.entity.item.ItemType;
import cz.petrchatrny.sopc.entity.item.OperationNotAllowedException;
import cz.petrchatrny.sopc.entity.map.StructureType;
import cz.petrchatrny.sopc.model.GameModel;
import cz.petrchatrny.sopc.view.InventoryCellFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private HBox playersHB;
    @FXML
    private ListView<Item> inventoryLV;
    @FXML
    private AnchorPane map;
    @FXML
    private Button magnetiteBT;
    @FXML
    private Button coalBT;
    @FXML
    private Button iceBT;
    @FXML
    private Button uraniniteBT;
    @FXML
    private Button steelBT;
    @FXML
    private Button fertilizerBT;
    @FXML
    private Button groxBoxBT;
    @FXML
    private Button spaceStationBT;
    @FXML
    private Button spaceShipBT;
    @FXML
    private Button wormHoleBT;

    private GameModel model;

    public GameController() {
    }

    public GameController(GameModel model) {
        this.model = model;
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

        ArrayList<Item> items = new ArrayList<>(model.getInventory().values());
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
        // TODO implement me
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
}
