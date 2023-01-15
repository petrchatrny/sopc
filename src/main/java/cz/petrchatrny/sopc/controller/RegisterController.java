package cz.petrchatrny.sopc.controller;

import cz.petrchatrny.sopc.App;
import cz.petrchatrny.sopc.SceneType;
import cz.petrchatrny.sopc.model.RegisterModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController implements Resultant {
    @FXML private Button registerBT;
    @FXML private ProgressIndicator progressIndicator;
    @FXML private TextField nicknameTF;
    @FXML private TextField emailTF;
    @FXML private PasswordField passwordTF;
    @FXML private PasswordField repeatPasswordTF;

    private final RegisterModel model;

    /**
     * RegisterController's constructor that is requiring RegisterModel. Dependency injection patter applied.
     *
     * @param model RegisterController's model
     */
    public RegisterController(RegisterModel model) {
        this.model = model;
        this.model.setResultant(this);
    }

    @FXML
    private void showLoginForm() {
        App.showScene(SceneType.LOGIN);
    }

    @FXML
    private void register() {
        showLoading(true);

        String nickname = nicknameTF.getText();
        String email = emailTF.getText();
        String password = passwordTF.getText();
        String repeatPassword = repeatPasswordTF.getText();

        model.register(nickname, email, password, repeatPassword);
        //model.register(nicknameTF.getText(), emailTF.getText(), passwordTF.getText(), repeatPasswordTF.getText());
    }

    @Override
    public void onTaskSucceeded() {
        showLoading(false);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Úspěšně zaregistrováno");
        alert.setHeaderText(null);
        alert.setContentText("Registrace proběhla úspěšně. Pro její dokončení ji potvrďte ve svém emailu.");
        alert.showAndWait();
        App.showScene(SceneType.LOGIN);
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
        registerBT.setDisable(value);
        progressIndicator.setVisible(value);
    }
}