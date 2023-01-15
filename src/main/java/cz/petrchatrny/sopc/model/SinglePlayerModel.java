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
import cz.petrchatrny.sopc.entity.map.Area;
import cz.petrchatrny.sopc.entity.map.Map;
import cz.petrchatrny.sopc.entity.map.MapStructure;
import cz.petrchatrny.sopc.entity.map.StructureType;
import cz.petrchatrny.sopc.entity.agent.Player;
import cz.petrchatrny.sopc.service.SessionService;
import cz.petrchatrny.sopc.view.Cell;
import cz.petrchatrny.sopc.view.Edge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.*;

public class SinglePlayerModel {
    private final ObservableMap<String, Agent> agents;
    private final Queue<String> queue;
    private final String LOCAL_PLAYER_ID = SessionService.getINSTANCE().getLocalUserId();
    private String agentOnTurnId;
    private TurnChangeListener turnChangeListener;
    private Map mapPlan;

    public SinglePlayerModel() {
        // add player
        this.agents = FXCollections.observableHashMap();
        this.agents.put(LOCAL_PLAYER_ID,
                new Player(LOCAL_PLAYER_ID, Color.BLUE, SessionService.getINSTANCE().getLocalUserUsername())
        );

        // add bots
        List<Agent> bots = List.of(
                new Bot(Color.RED, isLocalPlayerOnTurn -> nextTurn()),
                new Bot(Color.GREEN, isLocalPlayerOnTurn -> nextTurn()),
                new Bot(Color.GRAY, isLocalPlayerOnTurn -> nextTurn())
        );
        bots.forEach(agent -> agents.put(agent.getId(), agent));

        // setup queue
        queue = new LinkedList<>();
        queue.addAll(
                this.agents.values()
                        .stream()
                        .map(Agent::getId)
                        .toList()
        );

        // setup map
        mapPlan = new Map(400, 50, getAgents());
    }

    public void startGame() {
        // set model to bots
        SinglePlayerModel model = this;
        agents.values()
                .stream()
                .map(agent -> {
                    if (agent instanceof Bot) {
                        return (Bot) agent;
                    }
                    return null;
                })
                .filter(bot -> bot != null)
                .forEach(bot -> bot.setModel(model));

        // first player on turn
        Agent first = this.agents.get(queue.remove());
        agentOnTurnId = first.getId();
        first.onTurnStarted();
    }

    public void nextTurn() {
        agents.get(agentOnTurnId).onTurnEnded();
        queue.add(agentOnTurnId);
        agentOnTurnId = queue.remove();
        agents.get(agentOnTurnId).onTurnStarted();
        turnChangeListener.onTurnChanged(isLocalPlayerOnTurn());
    }

    public ObservableMap<ItemType, Item> getLocalPlayerInventory() {
        return agents.get(LOCAL_PLAYER_ID).getInventory().getItems();
    }

    public HashMap<ItemType, Integer> mineOres(int number) {
        HashMap<ItemType, Integer> localPlayerItems = new HashMap<>();
        List<Area> areas = mapPlan.getAreas()
                .stream()
                .filter(area -> area.getDiceNumber() == number)
                .filter(area -> !area.isGroxOccupied())
                .toList();

        for (Area area : areas) {
            area.getCells().forEach(cell -> {
                // add items to inventory of settlers
                Agent settler = cell.getSettler();
                if (settler != null) {
                    settler.getInventory().getItems().get(area.getOre()).increaseCount(); // TODO check null exception

                    // collect acquired items of local user
                    if (settler.getId().equals(LOCAL_PLAYER_ID)) {
                        if (!localPlayerItems.containsKey(area.getOre())) {
                            localPlayerItems.put(area.getOre(), 1);
                        } else {
                            localPlayerItems.put(area.getOre(), localPlayerItems.get(area.getOre()) + 1);
                        }
                    }
                }
            });
        }

        return localPlayerItems;
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
        ObservableMap<ItemType, Item> inventory = agents.get(agentOnTurnId).getInventory().getItems();
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
        ObservableMap<ItemType, Item> inventory = agents.get(agentOnTurnId).getInventory().getItems();
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

    public boolean buildStructure(MapStructure structure, StructureType type) throws OperationNotAllowedException {
        if (structure.getSettler() != null) {
            throw new OperationNotAllowedException("Toto pole je již obsazené.");
        }

        if (!enoughResourcesForBuildingStructure(type)) {
            throw new OperationNotAllowedException("Nedostatek surovin pro stavbu.");
        }

        if (!structureConnectsOnPrevious(structure)) {
            throw new OperationNotAllowedException("Stavby na sebe musejí navazovat.");
        }

        // remove required resource for building from inventory
        Agent agent = agents.get(agentOnTurnId);
        ObservableMap<ItemType, Item> inventory = agent.getInventory().getItems();
        Collection<ItemStruct> requiredResources = type.getRequiredResources();
        requiredResources.forEach(requiredResource -> {
            Item resource = inventory.get(requiredResource.type());
            resource.setCount(resource.getCount() - requiredResource.count());
        });

        // show settler in structure
        structure.setSettler(agent);

        // change number of built structure in settler
        switch (type) {
            case SPACE_STATION -> agent.increaseSpaceStations();
            case SPACESHIP -> agent.increaseSpaceShips();
        }

        // check game over
        return isGameOver(agent);
    }

    /**
     * The method checks whether the user on turn has enough resources to build the required structure.
     *
     * @param type type of structure
     * @return if user can build the structure
     */
    private boolean enoughResourcesForBuildingStructure(StructureType type) {
        ObservableMap<ItemType, Item> inventory = agents.get(agentOnTurnId).getInventory().getItems();
        Collection<ItemStruct> requiredResources = type.getRequiredResources();
        boolean canBuild = true;

        for (ItemStruct requiredResource : requiredResources) {
            if (inventory.get(requiredResource.type()).getCount() < requiredResource.count()) {
                canBuild = false;
                break;
            }
        }

        return canBuild;
    }

    /**
     * The method checks whether the selected structure in the map is connected to a structure already conquered by
     * the user on turn.
     *
     * @param structure clicked map structure
     * @return if selected structure is connected to other already conquered map structure
     */
    private boolean structureConnectsOnPrevious(MapStructure structure) {
        if (structure instanceof Cell) {
            return structureConnectsOnPrevious((Cell) structure);
        } else if (structure instanceof Edge) {
            return structureConnectsOnPrevious((Edge) structure);
        } else {
            return false;
        }
    }

    private boolean structureConnectsOnPrevious(Cell cell) {
        return cell.getConnections()
                .stream()
                .anyMatch(edge -> {
                    if (edge.getSettler() == null) {
                        return false;
                    }
                    return edge.getSettler().equals(agents.get(agentOnTurnId));
                });
    }

    private boolean structureConnectsOnPrevious(Edge edge) {
        Agent agent = agents.get(agentOnTurnId);

        return (edge.getFrom().getSettler() != null && edge.getFrom().getSettler().equals(agent)) ||
                (edge.getTo().getSettler() != null && edge.getTo().getSettler().equals(agent));
    }


    private void buyGroxBox() {
        // TODO implement me
    }

    private boolean isGameOver(Agent agent) {
        return agent.getSpaceShips() >= Settings.MAX_SPACESHIP_COUNT &&
                agent.getSpaceStations() >= Settings.MAX_SPACE_STATION_COUNT;
    }

    public ObservableList<Agent> getAgents() {
        return FXCollections.observableArrayList(agents.values());
    }

    public boolean isLocalPlayerOnTurn() {
        return agentOnTurnId.equals(LOCAL_PLAYER_ID);
    }

    public void setTurnChangeListener(TurnChangeListener turnChangeListener) {
        this.turnChangeListener = turnChangeListener;
    }

    public Map getMapPlan() {
        return mapPlan;
    }
}