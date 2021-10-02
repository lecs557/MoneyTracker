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

    public Loader() {
    }

    @Override
    public void run() {
        progress.set(0);

        initializeController();

        progress.set(1);

        Platform.runLater(() -> WindowManager.getInstance().changeSceneTo(AppStart.windows.LogIn));
    }

    private void initializeController() {
        ContentController.initialize();
        advanceProgress();
        DatabaseController.initialize("Aurum_Observa");
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
