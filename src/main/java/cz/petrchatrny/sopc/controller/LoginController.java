package cz.petrchatrny.sopc.controller;

import cz.petrchatrny.sopc.App;
import cz.petrchatrny.sopc.SceneType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {
    @FXML
    private TextField emailTF;
    @FXML
    private PasswordField passwordTF;

    @FXML
    private void showRegistrationForm(MouseEvent mouseEvent) {
        App.showScene(SceneType.REGISTER);
    }

    @FXML
    private void login(ActionEvent actionEvent) {
        // TODO
    }
}
