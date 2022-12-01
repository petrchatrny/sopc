module cz.petrchatrny.sopc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.kordamp.ikonli.javafx;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.fontawesome5;

    opens cz.petrchatrny.sopc to javafx.fxml;
    exports cz.petrchatrny.sopc;
}