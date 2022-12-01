package cz.petrchatrny.sopc.model;

import com.google.gson.JsonObject;
import cz.petrchatrny.sopc.controller.Resultant;
import cz.petrchatrny.sopc.service.ApiService;
import cz.petrchatrny.sopc.service.SessionService;
import javafx.concurrent.Task;

import java.util.concurrent.ExecutionException;

public class LoginModel {
    private Resultant resultant;

    public void login(String email, String password) {
        // create asynchronous task
        ApiService apiService = ApiService.getInstance();
        final Task<JsonObject> task = new Task<>() {
            @Override
            public JsonObject call() {
                try {
                    return apiService.userLogin(email, password).get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        // set task's result
        task.setOnSucceeded(event -> {
            JsonObject result = task.getValue();
            switch (result.get("message").getAsString()) {
                case "success" -> {
                    SessionService.getINSTANCE().createSession(result.getAsJsonObject("data"));
                    resultant.onTaskSucceeded();
                }
                case "wrong-credentials" -> resultant.onTaskFailed("Nesprávné uživatelské jméno nebo heslo.");
            }
        });
        task.setOnFailed(event -> resultant.onTaskFailed(StringConst.INTERNET_ERR_MSG));

        // start async task
        new Thread(task).start();
    }

    public void setResultant(Resultant resultant) {
        this.resultant = resultant;
    }
}
