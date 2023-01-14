package cz.petrchatrny.sopc.view;

import cz.petrchatrny.sopc.App;
import cz.petrchatrny.sopc.entity.agent.Agent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class AgentViewHolder extends AnchorPane {
    @FXML public Label usernameLB;
    @FXML public Label spaceStationsLB;
    @FXML public Label spaceShipsLB;
    @FXML public Label spaceStationsMaxLB;
    @FXML public Label spaceShipsMaxLB;
    @FXML public Circle color;
    @FXML public FontIcon flagIC;
    @FXML public Node agent;

    public AgentViewHolder(Agent player, int maxSpaceShip, int maxSpaceStation) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view-holder/agent.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            color.setFill(Paint.valueOf(player.getColor().getValue()));
            usernameLB.textProperty().bind(player.getUsername());
            spaceShipsLB.textProperty().bind(player.getSpaceShipsLabel());
            spaceStationsLB.textProperty().bind(player.getSpaceStationsLabel());
            flagIC.visibleProperty().bind(player.isOnTurn());

            spaceShipsMaxLB.textProperty().set(String.valueOf(maxSpaceShip));
            spaceStationsMaxLB.textProperty().set(String.valueOf(maxSpaceStation));
            getChildren().add(this.agent);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
