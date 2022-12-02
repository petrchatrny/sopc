package cz.petrchatrny.sopc.model;

import com.google.gson.JsonObject;
import cz.petrchatrny.sopc.service.ApiService;
import javafx.application.Platform;

import java.util.function.Predicate;

public class RegisterModel extends HttpModel {
    private final Predicate<String> emailValidator = s -> {
        return s.matches("^[a-zA-Z0-9~!$%^&*_=+}{'?.].*@[a-zA-Z0-9~!$%^&*_=+}{'?.].*\\.[a-zA-Z].*$");
    };
    private final Predicate<String> containsCharacters = s -> s.matches("^.*[a-zA-Z].*$");
    private final Predicate<String> containsNumbers = s -> s.matches("^.*[a-zA-Z].*$");
    private final Predicate<String> containsSpecialChars = s -> s.matches("^.*[`!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?~].*$");

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
        if (!validateInput(username, email, password, repeatPassword)) {
            return;
        }

        // send async HTTP request in a new thread
        ApiService.getInstance()
                .userRegister(username, email, password)
                .thenApplyAsync(this::processResponse)
                .exceptionallyAsync(this::processError);
    }

    private boolean validateInput(String username, String email, String password, String repeatPassword) {
        Predicate<String> passwordValidator = containsCharacters
                .and(containsNumbers)
                .and(containsSpecialChars);

        // username input validation
        if (username.length() < 4) {
            resultant.onTaskFailed("Uživatelské jméno musí mít alespoň 4 znaky.");
            return false;
        } else if (containsSpecialChars.test(username)) {
            resultant.onTaskFailed("Uživatelské jméno nesmí obsahovat speciální znaky.");
            return false;
        }

        // email input validation
        if (!emailValidator.test(email)) {
            resultant.onTaskFailed("Emailová adresa není ve správném formátu.");
            return false;
        }

        // password input validation
        if (password.length() < 12) {
            resultant.onTaskFailed("Heslo není dostatečně dlouhé.");
            return false;
        } else if (!passwordValidator.test(password)) {
            resultant.onTaskFailed("Heslo není dostatečně silné.");
            return false;
        } else if (!password.equals(repeatPassword)) {
            resultant.onTaskFailed("Hesla se neshodují.");
            return false;
        }
        return true;
    }

    private Object processResponse(JsonObject json) {
        // run in GUI thread
        Platform.runLater(() -> {
            switch (json.get("message").getAsString()) {
                case "success" -> resultant.onTaskSucceeded();
                case "email-in-use" -> resultant.onTaskFailed("Tato emailová adresa je již obsazena.");
                case "username-in-use" -> resultant.onTaskFailed("Toto uživatelské jméno je již obsazeno.");
            }
        });
        return null;
    }
}
