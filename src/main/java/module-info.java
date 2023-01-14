module cz.petrchatrny.sopc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.kordamp.ikonli.javafx;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.fontawesome5;
    requires java.net.http;
    requires com.google.gson;

    opens cz.petrchatrny.sopc to javafx.fxml;
    exports cz.petrchatrny.sopc;
    exports cz.petrchatrny.sopc.controller;
    exports cz.petrchatrny.sopc.model;
    exports cz.petrchatrny.sopc.entity.item;
    exports cz.petrchatrny.sopc.entity.agent;
    exports cz.petrchatrny.sopc.view;
    opens cz.petrchatrny.sopc.controller to javafx.fxml;
}