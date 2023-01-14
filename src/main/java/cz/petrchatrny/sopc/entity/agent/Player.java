package cz.petrchatrny.sopc.entity.agent;

public class Player extends Agent {

    public Player(String id, Color color, String username) {
        super(id, color, username);
    }

    @Override
    public void onTurnStarted() {
        setOnTurn(true);
    }

    @Override
    public void onTurnEnded() {
        setOnTurn(false);
    }
}