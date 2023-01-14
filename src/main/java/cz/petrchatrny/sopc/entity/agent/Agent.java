package cz.petrchatrny.sopc.entity.agent;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Agent {
    private final String id;
    private final Color color;
    private SimpleStringProperty username;
    private int spaceShips;
    private int spaceStations;
    private SimpleBooleanProperty isOnTurn;
    private Inventory inventory;
    private SimpleStringProperty spaceShipsLabel = new SimpleStringProperty();
    private SimpleStringProperty spaceStationsLabel = new SimpleStringProperty();

    public Agent(String id, Color color, String username) {
        this.id = id;
        this.username = new SimpleStringProperty(username);
        this.color = color;

        this.spaceShips = 2;
        this.spaceStations = 2;
        this.isOnTurn = new SimpleBooleanProperty(false);

        this.spaceShipsLabel.setValue("02");
        this.spaceStationsLabel.set("02");

        this.inventory = new Inventory();
    }

    public abstract void onTurnStarted();

    public abstract void onTurnEnded();

    public void increaseSpaceShips() {
        this.spaceShips++;
        this.spaceShipsLabel.set(resolveLabelValue(this.spaceShips));
    }

    public void increaseSpaceStations() {
        this.spaceStations++;
        this.spaceStationsLabel.set(resolveLabelValue(this.spaceStations));
    }

    private String resolveLabelValue(int count) {
        if (count < 10) {
            return "0" + count;
        }
        return String.valueOf(count);
    }

    // region GETTERS
    public String getId() {
        return id;
    }

    public SimpleStringProperty getUsername() {
        return username;
    }

    public Color getColor() {
        return color;
    }

    public int getSpaceShips() {
        return this.spaceShips;
    }

    public int getSpaceStations() {
        return spaceStations;
    }

    public SimpleBooleanProperty isOnTurn() {
        return isOnTurn;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public SimpleStringProperty getSpaceShipsLabel() {
        return spaceShipsLabel;
    }

    public SimpleStringProperty getSpaceStationsLabel() {
        return spaceStationsLabel;
    }

    // endregion

    // region SETTERS
    protected void setOnTurn(boolean onTurn) {
        isOnTurn.setValue(onTurn);
    }
    // endregion
}
