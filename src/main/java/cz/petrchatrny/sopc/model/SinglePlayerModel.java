package cz.petrchatrny.sopc.model;

import cz.petrchatrny.sopc.controller.TurnChangeListener;
import cz.petrchatrny.sopc.entity.item.Item;
import cz.petrchatrny.sopc.entity.item.ItemStruct;
import cz.petrchatrny.sopc.entity.item.ItemType;
import cz.petrchatrny.sopc.entity.item.OperationNotAllowedException;
import cz.petrchatrny.sopc.entity.item.ore.*;
import cz.petrchatrny.sopc.entity.agent.Agent;
import cz.petrchatrny.sopc.entity.agent.Bot;
import cz.petrchatrny.sopc.entity.agent.Color;
import cz.petrchatrny.sopc.view.map.StructureType;
import cz.petrchatrny.sopc.entity.agent.Player;
import cz.petrchatrny.sopc.service.SessionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SinglePlayerModel {
    private final ObservableMap<String, Agent> agents;
    private final Queue<String> queue;
    private final String LOCAL_PLAYER_ID = SessionService.getINSTANCE().getLocalUserId();
    private String playerOnTurnId;
    private TurnChangeListener turnChangeListener;

    public SinglePlayerModel() {
        // add player
        this.agents = FXCollections.observableHashMap();
        this.agents.put(LOCAL_PLAYER_ID, new Player(LOCAL_PLAYER_ID, Color.BLUE, "Prion"));

        // add bots
        List<Agent> bots = List.of(
                new Bot(Color.RED, isLocalPlayerOnTurn -> nextTurn()),
                new Bot(Color.GREEN, isLocalPlayerOnTurn -> nextTurn()),
                new Bot(Color.GRAY, isLocalPlayerOnTurn -> nextTurn())
        );
        bots.forEach(agent -> agents.put(agent.getId(), agent));

        // setup queue
        this.queue = new LinkedList<>();
        this.queue.addAll(
                this.agents.values()
                        .stream()
                        .map(Agent::getId)
                        .toList()
        );

        // first player on turn
        Agent first = this.agents.get(queue.remove());
        playerOnTurnId = first.getId();
        first.onTurnStarted();
    }

    public void nextTurn() {
        agents.get(playerOnTurnId).onTurnEnded();
        queue.add(playerOnTurnId);
        playerOnTurnId = queue.remove();
        agents.get(playerOnTurnId).onTurnStarted();
        turnChangeListener.onTurnChanged(isLocalPlayerOnTurn());
    }

    public ObservableMap<ItemType, Item> getLocalPlayerInventory() {
        return this.agents.get(LOCAL_PLAYER_ID).getInventory().getItems();
    }

    /**
     * Methods access item in inventory and tries to perform process operation. If items from processing are obtained,
     * it adds them into inventory.
     *
     * @param type item type
     * @throws OperationNotAllowedException when processed item is not instance of Ore
     * @see Ore
     */
    public void processOre(ItemType type) throws OperationNotAllowedException {
        ObservableMap<ItemType, Item> inventory = agents.get(playerOnTurnId).getInventory().getItems();
        Item ore = inventory.get(type);
        if (ore.getCount() <= 0) {
            return;
        }

        ore.setCount(ore.getCount() - 1);
        Collection<ItemStruct> results = ore.process();
        for (ItemStruct res : results) {
            inventory.get(res.type()).setCount(inventory.get(res.type()).getCount() + res.count());
        }
    }

    /**
     * Method access item in inventory and tries to create it. If all goes well, required items for creating this one
     * will be deducted from inventory
     *
     * @param type item to create
     * @throws OperationNotAllowedException when created item cannot be crafted
     */
    public void craftProduct(ItemType type) throws OperationNotAllowedException {
        ObservableMap<ItemType, Item> inventory = agents.get(playerOnTurnId).getInventory().getItems();
        Item product = inventory.get(type);
        Collection<ItemStruct> requiredResources = product.requiredResources();

        boolean canCraft = true;
        for (ItemStruct requiredResource : requiredResources) {
            if (inventory.get(requiredResource.type()).getCount() < requiredResource.count()) {
                canCraft = false;
                break;
            }
        }

        if (canCraft) {
            requiredResources.forEach(requiredResource -> {
                Item resource = inventory.get(requiredResource.type());
                resource.setCount(resource.getCount() - requiredResource.count());
            });
            product.setCount(product.getCount() + 1);
        }
    }

    private void buildStructure(StructureType type) {
        // TODO implement me
    }

    private void buyGroxBox() {
        // TODO implement me
    }

    public ObservableList<Agent> getAgents() {
        return FXCollections.observableArrayList(agents.values());
    }

    private boolean isLocalPlayerOnTurn() {
        return playerOnTurnId.equals(LOCAL_PLAYER_ID);
    }

    public void setTurnChangeListener(TurnChangeListener turnChangeListener) {
        this.turnChangeListener = turnChangeListener;
    }
}