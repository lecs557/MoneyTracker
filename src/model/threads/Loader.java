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
    private final boolean test;

    public Loader(boolean test) {
        this.test = test;
    }

    @Override
    public void run() {
        progress.set(0);
        initializeController();
        progress.set(1);
        Platform.runLater(() -> WindowManager.getInstance().changeSceneTo(AppStart.windows.LogIn));
    }

    private void initializeController() {
        if(test)
            DatabaseController.initialize("Test_db");
        else
            DatabaseController.initialize("MoneyTracker");
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
