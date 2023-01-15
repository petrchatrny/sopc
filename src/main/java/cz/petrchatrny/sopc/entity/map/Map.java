package cz.petrchatrny.sopc.entity.map;

import cz.petrchatrny.sopc.entity.agent.Agent;
import cz.petrchatrny.sopc.entity.item.ItemType;
import cz.petrchatrny.sopc.view.Cell;
import cz.petrchatrny.sopc.view.Edge;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Map {
    private final Graph graph = new Graph();
    private List<Area> areas = new ArrayList<>();
    private final List<List<String>> DEFAULT_SPACE_STATION_CELLS = List.of(
            List.of("E", "V"),
            List.of("Q", "M"),
            List.of("CH", "1"),
            List.of("B", "3")
    );

    public Map(int dx, int dy, List<Agent> agents) {
        initMap(dx, dy);
        initAreas();
        initDefaultSpaceStations(agents);
    }

    public void changeMapStructuresClickability(StructureType structure, boolean clickable, MapStructureClickListener clickListener) {
        String style;
        if (clickable) {
            style = "-fx-cursor: hand";
        } else {
            style = "-fx-cursor: default";
        }
        switch (structure) {
            case SPACE_STATION -> {
                for (Cell c : graph.getCells()) {
                    c.setStyle(style);
                    if (clickable) {
                        c.setOnMouseClicked(event -> clickListener.onCellClicked(c));
                    } else {
                        c.setOnMouseClicked(null);
                    }
                }
            }
            case SPACESHIP -> {
                for (Edge e : graph.getEdges()) {
                    e.setStyle(style);
                    if (clickable) {
                        e.setOnMouseClicked(event -> clickListener.onEdgeClicked(e));
                    } else {
                        e.setOnMouseClicked(null);
                    }
                }
            }
        }
    }

    private void initMap(double dx, double dy) {
        graph.addCell(new Cell("A", dx + 0, dy + 0));
        graph.addCell(new Cell("B", dx + 64.5, dy + 37.5));
        graph.addCell(new Cell("C", dx + 64.5, dy + 112.5));
        graph.addCell(new Cell("D", dx + 0, dy + 150));
        graph.addCell(new Cell("E", dx + -64.5, dy + 112.5));
        graph.addCell(new Cell("F", dx + -64.5, dy + 37.5));
        graph.addCell(new Cell("G", dx + 130, dy + 0));
        graph.addCell(new Cell("H", dx + 130, dy + 150));
        graph.addCell(new Cell("CH", dx + 195, dy + 37.5));
        graph.addCell(new Cell("I", dx + 195, dy + 112.5));
        graph.addCell(new Cell("J", dx + 260, dy + 0));
        graph.addCell(new Cell("K", dx + 260, dy + 150));
        graph.addCell(new Cell("L", dx + 324.5, dy + 37.5));
        graph.addCell(new Cell("M", dx + 324.5, dy + 112.5));
        graph.addCell(new Cell("N", dx + -130, dy + 150));
        graph.addCell(new Cell("O", dx + -130, dy + 225));
        graph.addCell(new Cell("P", dx + 0, dy + 225));
        graph.addCell(new Cell("Q", dx + -64.5, dy + 262.5));
        graph.addCell(new Cell("R", dx + 64.5, dy + 262.5));
        graph.addCell(new Cell("S", dx + 130, dy + 225));
        graph.addCell(new Cell("T", dx + 195, dy + 262.5));
        graph.addCell(new Cell("U", dx + 260, dy + 225));
        graph.addCell(new Cell("V", dx + 324.5, dy + 262.5));
        graph.addCell(new Cell("W", dx + 389.5, dy + 225));
        graph.addCell(new Cell("X", dx + 389.5, dy + 150));
        graph.addCell(new Cell("Z", dx + -64.5, dy + 337.5));
        graph.addCell(new Cell("0", dx + 0, dy + 375));
        graph.addCell(new Cell("1", dx + 64.5, dy + 337.5));
        graph.addCell(new Cell("2", dx + 130, dy + 375));
        graph.addCell(new Cell("3", dx + 195, dy + 337.5));
        graph.addCell(new Cell("4", dx + 260, dy + 375));
        graph.addCell(new Cell("5", dx + 324.5, dy + 337.5));

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("D", "E");
        graph.addEdge("E", "F");
        graph.addEdge("F", "A");

        graph.addEdge("B", "G");
        graph.addEdge("C", "H");
        graph.addEdge("G", "CH");
        graph.addEdge("CH", "I");
        graph.addEdge("H", "I");

        graph.addEdge("J", "CH");
        graph.addEdge("K", "I");
        graph.addEdge("L", "J");
        graph.addEdge("L", "M");
        graph.addEdge("K", "M");

        graph.addEdge("E", "N");
        graph.addEdge("N", "O");
        graph.addEdge("P", "Q");
        graph.addEdge("Q", "O");
        graph.addEdge("P", "D");

        graph.addEdge("R", "P");
        graph.addEdge("R", "S");
        graph.addEdge("H", "S");

        graph.addEdge("S", "T");
        graph.addEdge("U", "T");
        graph.addEdge("U", "K");

        graph.addEdge("U", "V");
        graph.addEdge("V", "W");
        graph.addEdge("W", "X");
        graph.addEdge("X", "M");

        graph.addEdge("Q", "Z");
        graph.addEdge("Z", "0");
        graph.addEdge("0", "1");
        graph.addEdge("1", "R");

        graph.addEdge("2", "1");
        graph.addEdge("2", "3");
        graph.addEdge("3", "T");

        graph.addEdge("3", "4");
        graph.addEdge("4", "5");
        graph.addEdge("5", "V");

        graph.update();
    }

    private void initAreas() {
        areas.addAll(List.of(
                new Area(ItemType.URANINITE, 4, false, getCells("A", "B", "C", "D", "E", "F")),
                new Area(ItemType.COAL, 1, false, getCells("G", "CH", "I", "H", "C", "B")),
                new Area(ItemType.URANINITE, 3, false, getCells("J", "L", "M", "K", "I", "CH")),
                new Area(ItemType.ICE, 1, false, getCells("E", "D", "P", "Q", "O", "N")),
                new Area(ItemType.DARK_MATTER, 5, false, getCells("C", "H", "S", "R", "P", "D")),
                new Area(ItemType.NONE, 6, true, getCells("I", "K", "U", "T", "S", "H")),
                new Area(ItemType.COAL, 2, false, getCells("M", "X", "W", "V", "U", "K")),
                new Area(ItemType.MAGNETITE, 4, false, getCells("P", "R", "1", "0", "Z", "Q")),
                new Area(ItemType.ICE, 2, false, getCells("S", "T", "3", "2", "1", "R")),
                new Area(ItemType.MAGNETITE, 3, false, getCells("U", "V", "5", "4", "3", "T"))
        ));
    }

    private void initDefaultSpaceStations(List<Agent> agents) {
        for (int i = 0; i < agents.size(); i++) {
            List<Cell> cells = getCells(DEFAULT_SPACE_STATION_CELLS.get(i));
            for (Cell c : cells) {
                c.setSettler(agents.get(i));
            }
        }
    }

    private List<Cell> getCells(Predicate<Cell> predicate) {
        return graph.getCellMap()
                .values()
                .stream()
                .filter(predicate)
                .toList();
    }

    private List<Cell> getCells(List<String> ids) {
        return getCells(cell -> ids.contains(cell.getCellId()));
    }

    private List<Cell> getCells(String... ids) {
        return getCells(cell -> Arrays.stream(ids)
                .toList()
                .contains(cell.getCellId())
        );
    }

    public Group getCanvas() {
        return graph.getCanvas();
    }

    public List<Area> getAreas() {
        return areas;
    }
}
