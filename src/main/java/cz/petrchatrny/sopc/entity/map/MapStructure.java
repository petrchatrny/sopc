package cz.petrchatrny.sopc.entity.map;

import cz.petrchatrny.sopc.entity.agent.Agent;

public interface MapStructure {
    Agent getSettler();

    void setSettler(Agent settler);
}
