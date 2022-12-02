package cz.petrchatrny.sopc.model;

import cz.petrchatrny.sopc.controller.Resultant;
import javafx.application.Platform;

public abstract class HttpModel {
    protected Resultant resultant;

    protected Object processError(Throwable throwable) {
        // log error
        System.out.println(throwable.getMessage()); // TODO replace with logger

        // run in GUI thread
        Platform.runLater(() -> resultant.onTaskFailed(StringConst.INTERNET_ERR_MSG));

        return null;
    }

    public void setResultant(Resultant resultant) {
        this.resultant = resultant;
    }
}
