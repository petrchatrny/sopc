package cz.petrchatrny.sopc.controller;

import cz.petrchatrny.sopc.App;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
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
    private boolean isPlacingStructure = false;
    private StructureType lastBuiltStructureType = StructureType.SPACESHIP;

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
        groxBoxBT.setDisable(true);
        spaceShipBT.setOnAction(event -> buildStructure(StructureType.SPACESHIP));
        spaceStationBT.setOnAction(event -> buildStructure(StructureType.SPACE_STATION));
        wormHoleBT.setOnAction(event -> buildStructure(StructureType.WORMHOLE));
        wormHoleBT.setDisable(true);
        diceBT.setOnAction(event -> rollDice());
        nextTurnBT.setOnAction(event -> model.nextTurn());

        // show map
        map.getChildren().add(model.getMapPlan().getCanvas());

        // disable selection of items in listview
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
        String style;
        if (!isPlacingStructure) {
            isPlacingStructure = true;
            lastBuiltStructureType = type;
            style = "-fx-background-color: #0a1e65";
        } else {
            isPlacingStructure = false;
            style = "-fx-background-color: #04103a";
        }

        Button b = switch (lastBuiltStructureType) {
            case SPACESHIP -> spaceShipBT;
            case SPACE_STATION -> spaceStationBT;
            case WORMHOLE -> wormHoleBT;
        };

        model.getMapPlan().changeMapStructuresClickability(type, isPlacingStructure, new MapStructureClickListener() {
            @Override
            public void onCellClicked(Cell cell) {
                try {
                    model.buildStructure(cell, StructureType.SPACE_STATION);
                    inventoryLV.refresh();
                } catch (OperationNotAllowedException e) {
                    showWarning(e);
                } finally {
                    buildStructure(type);
                }
            }

            @Override
            public void onEdgeClicked(Edge edge) {
                try {
                    model.buildStructure(edge, StructureType.SPACESHIP);
                    inventoryLV.refresh();
                } catch (OperationNotAllowedException e) {
                    showWarning(e);
                } finally {
                    buildStructure(type);
                }
            }
        });
        map.toFront();
        b.styleProperty().set(style);
    }

    private void buyGroxBox() {
        // TODO implement me
    }

    private void rollDice() {
        diceBT.setDisable(true);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("dialogs/dice-dialog.fxml"));

        // setup controller
        DiceDialogController controller = new DiceDialogController();
        controller.setOnDiceRollListener(number -> {
            controller.showAcquiredItems(model.mineOres(number));
            inventoryLV.refresh();
        });

        // assign controller to dialog
        fxmlLoader.setController(controller);

        try {
            // show new dialog window
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showInvalidOperationError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chyba");
        alert.setHeaderText("Chyba");
        alert.setContentText("Operace není povolena.");
        alert.showAndWait();
    }

    private void showWarning(Exception e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Varování");
        alert.setHeaderText("Varování");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    @Override
    public void onTurnChanged(boolean isLocalPlayerOnTurn) {
        controlPanel.setVisible(isLocalPlayerOnTurn);
        diceBT.setDisable(false);
    }
}
