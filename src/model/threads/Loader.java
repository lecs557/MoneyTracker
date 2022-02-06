package model.threads;

import controller.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.AppStart;

public class Loader extends Thread {

    private int currentThingToLoad;
    private int thingsToLoad = 5;
    private final SimpleDoubleProperty progress = new SimpleDoubleProperty();
    private final String databaseName;

    public Loader(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public void run() {
        progress.set(0);
        initializeController();
        progress.set(1);
        Platform.runLater(() -> WindowManager.getInstance().changeSceneTo(AppStart.windows.LogIn));
    }

    private void initializeController() {
        DatabaseController.initialize(databaseName);
        advanceProgress();
        IOController.initialize();
        advanceProgress();
        ProfileAccountManager.initialize();
        advanceProgress();
        ViewController.initialize();
    }

    private void advanceProgress(){
        currentThingToLoad++;
        progress.set(currentThingToLoad / (double)thingsToLoad);
    }

    public SimpleDoubleProperty progressProperty() {
        return progress;
    }

}
