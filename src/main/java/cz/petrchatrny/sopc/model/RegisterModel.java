package cz.petrchatrny.sopc.model;

import com.google.gson.JsonObject;
import cz.petrchatrny.sopc.controller.Resultant;
import cz.petrchatrny.sopc.service.ApiService;
import javafx.concurrent.Task;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

public class RegisterModel {
    private Resultant resultant;
    private Predicate<String> emailValidator = s -> {
        return s.matches("^[a-zA-Z0-9~!$%^&*_=+}{'?.].*@[a-zA-Z0-9~!$%^&*_=+}{'?.].*\\.[a-zA-Z].*$");
    };
    private Predicate<String> containsCharacters = s -> s.matches("^.*[a-zA-Z].*$");
    private Predicate<String> containsNumbers = s -> s.matches("^.*[a-zA-Z].*$");
    private Predicate<String> containsSpecialChars = s -> s.matches("^.*[`!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~].*$");

    /**
     * Method validates user's input and if all goes well it calls ApiService to register new user. Otherwise, it
     * calls resultant's error (@see Resultant) method with a corresponding message.
     *
     * @param username       user's username
     * @param email          user's email address
     * @param password       user's password in plaintext
     * @param repeatPassword user's repeated password to check the first one
     */
    public void register(String username, String email, String password, String repeatPassword) {
        Predicate<String> passwordValidator = containsCharacters
                .and(containsNumbers)
                .and(containsSpecialChars);

        // username input validation
        if (username.length() < 4) {
            resultant.onTaskFailed("Uživatelské jméno musí mít alespoň 4 znaky.");
            return;
        } else if (containsSpecialChars.test(username)) {
            resultant.onTaskFailed("Uživatelské jméno nesmí obsahovat speciální znaky.");
            return;
        }

        // email input validation
        if (!emailValidator.test(email)) {
            resultant.onTaskFailed("Emailová adresa není ve správném formátu.");
            return;
        }

        // password input validation
        if (password.length() < 12) {
            resultant.onTaskFailed("Heslo není dostatečně dlouhé.");
            return;
        } else if (!passwordValidator.test(password)) {
            resultant.onTaskFailed("Heslo není dostatečně silné.");
            return;
        } else if (!password.equals(repeatPassword)) {
            resultant.onTaskFailed("Hesla se neshodují.");
            return;
        }

        // create asynchronous task
        ApiService apiService = ApiService.getInstance();
        final Task<CompletableFuture<JsonObject>> task = new Task<>() {
            @Override
            public CompletableFuture<JsonObject> call() {
                return apiService.userRegister(username, email, password);
            }
        };

        // set task's result
        task.setOnSucceeded(event -> {
            var response = task.getValue();
            try {
                JsonObject result = response.get();
                switch (result.get("message").getAsString()) {
                    case "success" -> resultant.onTaskSucceeded();
                    case "email-in-use" -> resultant.onTaskFailed("Tato emailová adresa je již obsazena.");
                    case "username-in-use" -> resultant.onTaskFailed("Toto uživatelské jméno je již obsazeno.");
                }
            } catch (InterruptedException | ExecutionException e) {
                resultant.onTaskFailed("Nastala neznámá chyba při komunikaci se serverem.",
                        "Zkontrolujte své připojení k internetu a zkuste to znovu. Pokud problém přetrvává, zkuste" +
                                " restartovat aplikaci. Pokud i tak problém stále přetrvává, může být chyba na straně " +
                                " našich serverů.");
                throw new RuntimeException(e);
            }
        });
        task.setOnFailed(event -> resultant.onTaskFailed("Nastala neznámá chyba při komunikaci se serverem.",
                "Zkontrolujte své připojení k internetu a zkuste to znovu. Pokud problém přetrvává, zkuste" +
                        " restartovat aplikaci. Pokud i tak problém stále přetrvává, může být chyba na straně " +
                        " našich serverů."));

        // start async task
        new Thread(task).start();
    }

    public void setResultant(Resultant resultant) {
        this.resultant = resultant;
    }
}
