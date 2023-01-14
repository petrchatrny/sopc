package cz.petrchatrny.sopc;

public enum SceneType {
    LOGIN("Přihlásit se"),
    REGISTER("Zaregistrovat se"),
    HOME("Osadníci z Proxima Centauri"),
    LOBBY("Čekárna"),
    SINGLE_PLAYER_GAME("Osadníci z Proxima Centauri"),
    MULTI_PLAYER_GAME("Osadníci z Proxima Centauri"),
    LOADING("Osadníci z Proxima Centauri");

    private String title;

    SceneType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
