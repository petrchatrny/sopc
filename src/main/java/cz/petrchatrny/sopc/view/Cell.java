package cz.petrchatrny.sopc.view;

import cz.petrchatrny.sopc.entity.agent.Agent;
import cz.petrchatrny.sopc.entity.map.MapStructure;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Cell extends Pane implements MapStructure {
    private String cellId;
    private List<Edge> connections = new ArrayList<>();
    private Circle view;
    private Agent settler;

    public Cell(String cellId, double x, double y) {
        this.cellId = cellId;

        Circle circle = new Circle(15);
        circle.setStroke(Color.WHITE);
        //circle.setFill(new ImagePattern(new Text(cellId).snapshot(null, null)));
        circle.setFill(Color.WHITE);

        this.view = circle;
        getChildren().add(view);
        relocate(x, y);
    }

    public String getCellId() {
        return cellId;
    }

    public Node getView() {
        return this.view;
    }

    public List<Edge> getConnections() {
        return connections;
    }

    @Override
    public Agent getSettler() {
        return settler;
    }

    @Override
    public void setSettler(Agent settler) {
        this.settler = settler;
        view.setFill(Color.valueOf(settler.getColor().getValue()));
    }
}
