package cz.petrchatrny.sopc.entity.map;

import cz.petrchatrny.sopc.view.Cell;
import cz.petrchatrny.sopc.view.Edge;

public interface MapStructureClickListener {
    void onCellClicked(Cell cell);

    void onEdgeClicked(Edge edge);
}
