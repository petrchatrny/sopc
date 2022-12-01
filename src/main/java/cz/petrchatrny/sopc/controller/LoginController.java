package cz.petrchatrny.sopc.controller;

import cz.petrchatrny.sopc.App;
import cz.petrchatrny.sopc.SceneType;
import cz.petrchatrny.sopc.model.LoginModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController implements Resultant {
    public ProgressIndicator progressIndicator;
    @FXML
    private Button loginBT;
    private LoginModel model;
    @FXML
    private TextField emailTF;
    @FXML
    private PasswordField passwordTF;

    public LoginController() {
    }

    public LoginController(LoginModel model) {
        this.model = model;
        this.model.setResultant(this);
    }

    @FXML
    private void showRegistrationForm() {
        App.showScene(SceneType.REGISTER);
    }

    @FXML
    private void login() {
        showLoading(true);
        model.login(emailTF.getText(), passwordTF.getText());
    }

    @Override
    public void onTaskSucceeded() {
        showLoading(false);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Úspěšně přihlášeno");
        alert.setHeaderText(null);
        alert.setContentText("Přihlášení proběhlo úspěšně!");
        alert.showAndWait();

        App.showScene(SceneType.HOME);
    }

    @Override
    public void onTaskFailed(String header, String text) {
        showLoading(false);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chyba");
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private void showLoading(boolean value) {
        loginBT.setDisable(value);
        progressIndicator.setVisible(value);
    }
}
