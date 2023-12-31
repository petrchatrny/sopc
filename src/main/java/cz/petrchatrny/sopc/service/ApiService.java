package cz.petrchatrny.sopc.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

/**
 * Service for sending HTTP requests to REST API. Singleton design pattern applied.
 *
 * @author "Petr Chatrný"
 * @version 1.0, 28. 11. 2022
 */
public class ApiService {
    /**
     * base URL of used REST API
     **/
    private static final String BASE_URL = "https://sopc-api.onrender.com";
    /**
     * service singleton instance
     **/
    private static ApiService INSTANCE = null;
    /**
     * HTTP client for sending requests
     **/
    private HttpClient client;
    /**
     * function for parsing json string into JsonObject
     */
    private final Function<String, JsonObject> jsonParser = s -> JsonParser.parseString(s).getAsJsonObject();

    private ApiService() {
        ExecutorService executorService = Executors.newFixedThreadPool(6);

        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .executor(executorService)
                .build();
    }

    public static ApiService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApiService();
        }
        return INSTANCE;
    }

    /**
     * Method for sending asynchronous requests from client to API. Response data are accepted as JsonObject in
     * CompletableFuture. In case of POST and PUT request is method able to send JSON data inside request body.
     *
     * @param method  HTTP method
     * @param route   specific route of API
     * @param data    json data to send inside the request body
     * @param cookies map of http cookies
     * @return API response in JSON format
     * @see HttpMethod
     */
    private CompletableFuture<JsonObject> sendAsyncRequest(HttpMethod method, String route, JsonObject data, Map<String, String> cookies) {
        // setup request
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + route))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json");

        if (cookies != null) {
            for (Map.Entry<String, String> cookie : cookies.entrySet()) {
                requestBuilder.header("Cookie", cookie.getKey() + "=" + cookie.getValue());
            }
        }

        switch (method) {
            case GET -> requestBuilder.GET();
            case POST -> requestBuilder.POST(HttpRequest.BodyPublishers.ofString(data.toString()));
            case PUT -> requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(data.toString()));
            case DELETE -> requestBuilder.DELETE();
        }
        HttpRequest request = requestBuilder.build();

        // send request
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(jsonParser);
    }

    /**
     * Method for sending asynchronous requests from client to API. Response data are accepted as JsonObject in
     * CompletableFuture. In case of POST and PUT request is method able to send JSON data inside request body.
     *
     * @param method HTTP method @see HttpMethod
     * @param route  specific route of API
     * @param data   json data to send inside the request body
     * @return API response in JSON format
     */
    private CompletableFuture<JsonObject> sendAsyncRequest(HttpMethod method, String route, JsonObject data) {
        return sendAsyncRequest(method, route, data, null);
    }

    /**
     * Method for registering user. Response body contains result message if request was successful.
     *
     * @param username user's game nickname
     * @param email    user's email address
     * @param password user's password in plain text
     * @return API response in JSON format
     * @http POST /users/register
     */
    public CompletableFuture<JsonObject> userRegister(String username, String email, String password) {
        // jsonify body
        JsonObject json = new JsonObject();
        json.addProperty("username", username);
        json.addProperty("email", email);
        json.addProperty("password", password);

        return sendAsyncRequest(HttpMethod.POST, "/users/register", json);
    }


    /**
     * Method for user authentication. If authentication is successful, response body contains detailed data about
     * user. Otherwise, body contains error message.
     *
     * @param email    user's email address
     * @param password user's password in plain text
     * @return API response in JSON format
     * @http POST /users/login
     */
    public CompletableFuture<JsonObject> userLogin(String email, String password) {
        // jsonify body
        JsonObject json = new JsonObject();
        json.addProperty("email", email);
        json.addProperty("password", password);

        return sendAsyncRequest(HttpMethod.POST, "/users/login", json);
    }

    /**
     * Method for checking validity check of authentication. It sends jwt token to server where is token validated.
     *
     * @param jwt json web token
     * @return API response in JSON format
     * @http GET /users/authenticate
     */
    public CompletableFuture<JsonObject> authenticateUser(String jwt) {
        Map<String, String> cookies = new HashMap<>();
        cookies.put("auth", jwt);
        return sendAsyncRequest(HttpMethod.GET, "/users/authenticate", null, cookies);
    }
}