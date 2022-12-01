package cz.petrchatrny.sopc.controller;

import cz.petrchatrny.sopc.App;
import cz.petrchatrny.sopc.SceneType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RegisterController {
    @FXML
    private TextField nicknameTF;
    @FXML
    private TextField emailTF;
    @FXML
    private PasswordField passwordTF;
    @FXML
    private PasswordField repeatPasswordTF;

    @FXML
    private void showLoginForm(MouseEvent mouseEvent) {
        App.showScene(SceneType.LOGIN);
    }

    @FXML
    private void register(ActionEvent actionEvent) {
        // todo
    }
}