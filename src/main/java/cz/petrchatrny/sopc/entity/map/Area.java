package cz.petrchatrny.sopc.entity.map;

import cz.petrchatrny.sopc.entity.item.ItemType;
import cz.petrchatrny.sopc.view.Cell;

import java.util.List;

public class Area {
    private ItemType ore;
    private int diceNumber;
    private boolean isGroxOccupied;
    private List<Cell> cells;

    public Area(ItemType ore, int diceNumber, boolean isGroxOccupied, List<Cell> cells) {
        this.ore = ore;
        this.diceNumber = diceNumber;
        this.isGroxOccupied = isGroxOccupied;
        this.cells = cells;
    }

    public ItemType getOre() {
        return ore;
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public boolean isGroxOccupied() {
        return isGroxOccupied;
    }

    public void setGroxOccupied(boolean groxOccupied) {
        isGroxOccupied = groxOccupied;
    }
}
