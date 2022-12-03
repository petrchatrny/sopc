package cz.petrchatrny.sopc.service;

import cz.petrchatrny.sopc.MockUser;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class ApiServiceTest {
    private MockUser wrongUser = new MockUser("BibloPytlík", "bilbo.pytlik@pytlov", "SauronJeSlabko123");
    private MockUser existingUser = new MockUser("petrchatrny", "xchatrny@mendelu.cz", "SilneHeslo");
    private MockUser newUser = new MockUser("chatrnypetr", "p.chatrny@seznam.cz", "SilneHeslo");

    @Test
    void userLogin() {
        ApiService apiService = ApiService.getInstance();
        var res = apiService.userLogin(existingUser.email, existingUser.password);
        try {
            assertEquals("success", res.get()
                    .get("message")
                    .getAsString());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void userRegister() {
        ApiService apiService = ApiService.getInstance();
        var res = apiService.userRegister(existingUser.username, "string", "string");
        try {
            assertEquals("username-in-use", res.get()
                    .get("message")
                    .getAsString());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        res = apiService.userRegister("string", existingUser.email, "string");
        try {
            assertEquals("email-in-use", res.get()
                    .get("message")
                    .getAsString());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        res = apiService.userRegister(newUser.username, wrongUser.email, newUser.password);
        try {
            assertEquals("invalid-email", res.get()
                    .get("message")
                    .getAsString());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        res = apiService.userRegister(newUser.username, newUser.email, newUser.password);
        try {
            assertEquals("success", res.get()
                    .get("message")
                    .getAsString());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}