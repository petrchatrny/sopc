package cz.petrchatrny.sopc.model;

import com.google.gson.JsonObject;
import cz.petrchatrny.sopc.service.ApiService;
import cz.petrchatrny.sopc.service.SessionService;
import javafx.application.Platform;

public class LoginModel extends HttpModel {

    public void login(String email, String password) {
        // send async HTTP request in a new thread
        ApiService.getInstance()
                .userLogin(email, password)
                .thenApplyAsync(this::processResponse)
                .exceptionally(this::processError);
    }

    private Object processResponse(JsonObject json) {
        // run in GUI thread
        Platform.runLater(() -> {
            switch (json.get("message").getAsString()) {
                case "success" -> {
                    SessionService.getINSTANCE()
                            .createSession(json.getAsJsonObject("data"));
                    resultant.onTaskSucceeded();
                }
                case "wrong-credentials" -> resultant.onTaskFailed("Nesprávné uživatelské jméno nebo heslo.");
            }
        });

        return null;
    }
}
