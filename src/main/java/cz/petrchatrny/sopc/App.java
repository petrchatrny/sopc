package cz.petrchatrny.sopc;

import cz.petrchatrny.sopc.controller.GameController;
import cz.petrchatrny.sopc.controller.HomeController;
import cz.petrchatrny.sopc.controller.LoginController;
import cz.petrchatrny.sopc.controller.RegisterController;
import cz.petrchatrny.sopc.model.SinglePlayerModel;
import cz.petrchatrny.sopc.model.LoginModel;
import cz.petrchatrny.sopc.model.RegisterModel;
import cz.petrchatrny.sopc.service.ApiService;
import cz.petrchatrny.sopc.service.SessionService;
import javafx.application.Application;
import javafx.application.Platform;
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

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        App.stage = stage;
        stage.setResizable(false);

        // app icon
        URL url = getClass().getResource("images/icon.png");
        stage.getIcons().add(new Image(String.valueOf(url)));

        // show default scene
        showScene(SceneType.LOADING);
        showDefaultSceneBasedOnSessionData();

        // close http client after GUI close
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
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
                loader.setControllerFactory(t -> new LoginController(new LoginModel()));
            }
            case REGISTER -> {
                loader = new FXMLLoader(App.class.getResource("scenes/register-scene.fxml"));
                loader.setControllerFactory(t -> new RegisterController(new RegisterModel()));
            }
            case HOME -> {
                loader = new FXMLLoader(App.class.getResource("scenes/home-scene.fxml"));
                loader.setControllerFactory(t -> new HomeController());
            }
            case LOBBY -> {
                // TODO lobby for multiplayer
            }
            case SINGLE_PLAYER_GAME -> {
                loader = new FXMLLoader(App.class.getResource("scenes/game-scene.fxml"));
                loader.setControllerFactory(t -> new GameController(new SinglePlayerModel()));
            }
            case MULTI_PLAYER_GAME -> {
                // TODO multiplayer
            }
            case LOADING -> loader = new FXMLLoader(App.class.getResource("scenes/loading-scene.fxml"));
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

    /**
     * Method checks if application's session file exits. If yes, it sends HTTP request for authentication user in API.
     */
    private static void showDefaultSceneBasedOnSessionData() {
        String jwt = SessionService.getINSTANCE().getJwt();
        if (jwt == null) {
            showScene(SceneType.LOGIN);
            return;
        }

        // send async HTTP request in a new thread
        ApiService.getInstance()
                .authenticateUser(jwt)
                .thenApplyAsync(json -> {
                    Platform.runLater(() -> { // run in GUI thread
                        if (json.get("message").getAsString().equals("success")) {
                            showScene(SceneType.HOME);
                        } else {
                            showScene(SceneType.LOGIN);
                        }
                    });
                    return null;
                }).exceptionallyAsync(throwable -> {
                    Platform.runLater(() -> // run in GUI thread
                            showScene(SceneType.LOGIN));
                    return null;
                });
    }
}