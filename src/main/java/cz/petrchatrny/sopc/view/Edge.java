package cz.petrchatrny.sopc.view;

import cz.petrchatrny.sopc.entity.agent.Agent;
import cz.petrchatrny.sopc.entity.map.MapStructure;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Edge extends Group implements MapStructure {
    private Cell from, to;
    private Line line;
    private Agent settler;

    public Edge(Cell from, Cell to) {
        this.from = from;
        this.to = to;

        from.getConnections().add(this);
        to.getConnections().add(this);

        line = new Line();
        line.setStrokeWidth(4);
        line.setStroke(Color.WHITE);

        // dashed line texture
        //line.getStrokeDashArray().addAll(12d, 21d);

        line.startXProperty().bind(from.layoutXProperty());
        line.startYProperty().bind(from.layoutYProperty());
        line.endXProperty().bind(to.layoutXProperty());
        line.endYProperty().bind(to.layoutYProperty());

        getChildren().add(line);
    }

    public Cell getFrom() {
        return from;
    }

    public Cell getTo() {
        return to;
    }

    @Override
    public Agent getSettler() {
        return settler;
    }

    @Override
    public void setSettler(Agent settler) {
        this.settler = settler;
        this.line.setStroke(Color.valueOf(settler.getColor().getValue()));
    }
}