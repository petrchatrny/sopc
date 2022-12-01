package cz.petrchatrny.sopc.controller;

public interface Resultant {
    void onTaskSucceeded();

    default void onTaskFailed(String text) {
        onTaskFailed(null, text);
    }

    void onTaskFailed(String header, String text);
}
