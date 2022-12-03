package cz.petrchatrny.sopc;

import javafx.application.Application;
import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void showScene() {
        Thread t = new Thread("JavaFX Init Thread") {
            @Override
            public void run() {
                Application.launch(App.class);
                for (SceneType scene : SceneType.values()) {
                    App.showScene(scene);
                }
            }
        };
        t.start();
    }
}