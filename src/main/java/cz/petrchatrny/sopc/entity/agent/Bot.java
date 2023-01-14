package cz.petrchatrny.sopc.entity.agent;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Bot extends Agent {
    public Bot(Color color) {
        super(UUID.randomUUID().toString(), color, randomUsername());
    }

    private static String randomUsername() {
        List<String> names = List.of("Emil", "Amálie", "Rozárka", "Ema", "Vašek");
        Random rng = new Random();
        return names.get(rng.nextInt(names.size())) + " [BOT]";
    }

    @Override
    public void onTurnStarted() {
        // TODO do something smart
        setOnTurn(true);
        increaseSpaceShips();
        increaseSpaceStations();
    }

    @Override
    public void onTurnEnded() {
        setOnTurn(false);
    }
}
