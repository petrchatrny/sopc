package cz.petrchatrny.sopc.entity.agent;

import cz.petrchatrny.sopc.controller.TurnChangeListener;
import cz.petrchatrny.sopc.model.SinglePlayerModel;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Bot extends Agent {
    private TurnChangeListener turnChangeListener;
    private SinglePlayerModel model;

    public Bot(Color color, TurnChangeListener turnChangeListener) {
        super(UUID.randomUUID().toString(), color, randomUsername());
        this.turnChangeListener = turnChangeListener;
    }

    private static String randomUsername() {
        List<String> names = List.of("Emil", "Amálie", "Rozárka", "Ema", "Vašek");
        Random rng = new Random();
        return names.get(rng.nextInt(names.size())) + " [BOT]";
    }

    @Override
    public void onTurnStarted() {
        // TODO implement BOT logic
        setOnTurn(true);
        model.mineOres(new Random().nextInt(1, 6));
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                turnChangeListener.onTurnChanged(false);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (RuntimeException e) {
                // TODO Not on FX application thread
            }
        });
        thread.start();
    }

    @Override
    public void onTurnEnded() {
        setOnTurn(false);
    }

    public void setModel(SinglePlayerModel model) {
        this.model = model;
    }
}
