package cz.petrchatrny.sopc.entity.map;

import cz.petrchatrny.sopc.view.Cell;
import cz.petrchatrny.sopc.view.Edge;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Graph {
    private List<Cell> cells;
    private List<Edge> edges;
    private Map<String, Cell> cellMap;
    private final Group canvas;

    public Graph() {
        canvas = new Group();

        cells = new ArrayList<>();
        edges = new ArrayList<>();
        cellMap = new HashMap<>(); // <id,cell>
    }

    public Group getCanvas() {
        return this.canvas;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Map<String, Cell> getCellMap() {
        return cellMap;
    }

    public void update() {
        canvas.getChildren().addAll(edges);
        canvas.getChildren().addAll(cells);
    }

    public void addCell(Cell cell) {
        cells.add(cell);
        cellMap.put(cell.getCellId(), cell);
    }

    public void addEdge(String sourceId, String targetId) {
        Cell sourceCell = cellMap.get(sourceId);
        Cell targetCell = cellMap.get(targetId);

        Edge edge = new Edge(sourceCell, targetCell);
        edges.add(edge);
    }
}

