package cz.petrchatrny.sopc.controller;

import cz.petrchatrny.sopc.App;
import cz.petrchatrny.sopc.SceneType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeController {
    @FXML
    private void onSinglePlayerSelected(ActionEvent actionEvent) {
        App.showScene(SceneType.SINGLE_PLAYER_GAME);
    }

    @FXML
    private void onMultiPlayerSelected(ActionEvent actionEvent) {
        // TODO
    }
}
