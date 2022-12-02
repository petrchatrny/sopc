package cz.petrchatrny.sopc.model;

import cz.petrchatrny.sopc.controller.Resultant;
import cz.petrchatrny.sopc.service.ApiService;
import cz.petrchatrny.sopc.service.SessionService;
import javafx.application.Platform;

public class LoginModel {
    private Resultant resultant;

    public void login(String email, String password) {
        // send async HTTP request in a new thread
        ApiService.getInstance()
                .userLogin(email, password)
                .thenApplyAsync(json -> {
                    Platform.runLater(() -> {  // run in GUI thread
                        switch (json.get("message").getAsString()) {
                            case "success" -> {
                                SessionService.getINSTANCE().createSession(json.getAsJsonObject("data"));
                                resultant.onTaskSucceeded();
                            }
                            case "wrong-credentials" ->
                                    resultant.onTaskFailed("Nesprávné uživatelské jméno nebo heslo.");
                        }
                    });
                    return null;
                }).exceptionally(throwable -> {
                    Platform.runLater(() ->  // run in GUI thread
                            resultant.onTaskFailed(StringConst.INTERNET_ERR_MSG));
                    return null;
                });
    }

    public void setResultant(Resultant resultant) {
        this.resultant = resultant;
    }
}
