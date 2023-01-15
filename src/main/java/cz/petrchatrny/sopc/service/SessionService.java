package cz.petrchatrny.sopc.service;

import com.google.gson.*;

import java.io.*;

/**
 * Service for accessing application's session file. File's type is json.
 *
 * @author "Petr Chatrný"
 * @version 1.0, 27. 11. 2022
 */
public class SessionService {
    /**
     * singleton instance of service
     */
    private static SessionService INSTANCE = null;
    /**
     * path to file on disk
     */
    private static final String FILE_PATH = "session.json";

    /**
     * private singleton constructor creates session file if it does not exist
     */
    private SessionService() {
        File file = new File(FILE_PATH);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionService getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new SessionService();
        }
        return INSTANCE;
    }

    /**
     * @return json web token from session file
     */
    public String getJwt() {
        try (FileReader file = new FileReader("session.json")) {
            JsonElement fileData = JsonParser.parseReader(file);
            if (fileData instanceof JsonNull) {
                return null;
            } else {
                JsonObject json = fileData.getAsJsonObject();
                return json.get("auth").getAsString();
            }
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Method for creating record in application's session file
     *
     * @param json user data and json web token
     */
    public void createSession(JsonObject json) {
        try (FileWriter file = new FileWriter("session.json")) {
            file.write(new Gson().toJson(json));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLocalUserId() {
        return getLocalUserAttribute("_id");
    }

    public String getLocalUserUsername() {
        return getLocalUserAttribute("username");
    }

    private String getLocalUserAttribute(String attribute) {
        try (FileReader file = new FileReader("session.json")) {
            JsonElement fileData = JsonParser.parseReader(file);
            if (fileData instanceof JsonNull) {
                return null;
            } else {
                JsonObject json = fileData.getAsJsonObject();
                return json.get("user").getAsJsonObject().get(attribute).getAsString();
            }
        } catch (IOException e) {
            return null;
        }
    }
}
