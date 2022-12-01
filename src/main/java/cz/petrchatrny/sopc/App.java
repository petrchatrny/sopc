package cz.petrchatrny.sopc;

import cz.petrchatrny.sopc.controller.GameController;
import cz.petrchatrny.sopc.controller.HomeController;
import cz.petrchatrny.sopc.controller.LoginController;
import cz.petrchatrny.sopc.controller.RegisterController;
import cz.petrchatrny.sopc.model.RegisterModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Main class of JavaFX application.
 *
 * @author "Petr Chatrný"
 * @version 1.0, 27. 11. 2022
 */
public class App extends Application {
    /**
     * stage of application for scene changing
     */
    private static Stage stage;

    @Override
    public void start(Stage stage) {
        App.stage = stage;
        stage.setResizable(false);

        // app icon
        URL url = getClass().getResource("images/icon.png");
        stage.getIcons().add(new Image(String.valueOf(url)));

        // show default scene
        showScene(SceneType.REGISTER);
    }

    /**
     * Method for navigating through application. It loads new FXML scene and replaces current scene in stage with
     * this new one. Selected scene is determined by sceneType parameter.
     *
     * @param sceneType scene to be displayed
     */
    public static void showScene(SceneType sceneType) {
        FXMLLoader loader = null;
        switch (sceneType) {
            case LOGIN -> {
                loader = new FXMLLoader(App.class.getResource("scenes/login-scene.fxml"));
                loader.setControllerFactory(t -> new LoginController());
            }
            case REGISTER -> {
                loader = new FXMLLoader(App.class.getResource("scenes/register-scene.fxml"));
                loader.setControllerFactory(t -> new RegisterController(new RegisterModel()));
            }
            case HOME -> {
                loader = new FXMLLoader(App.class.getResource("scenes/home-scene.fxml"));
                loader.setControllerFactory(t -> new HomeController());
            }
            case GAME -> {
                loader = new FXMLLoader(App.class.getResource("scenes/game-scene.fxml"));
                loader.setControllerFactory(t -> new GameController());
            }
        }
        Scene scene;
        try {
            Parent root = loader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.setTitle(sceneType.getTitle());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}