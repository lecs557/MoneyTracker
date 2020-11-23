package model.threads;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.*;

import java.util.ArrayList;

public class Renamer extends Thread {

    private SimpleDoubleProperty progressYear = new SimpleDoubleProperty();
    private SimpleDoubleProperty progressTransaction = new SimpleDoubleProperty();
    private SimpleBooleanProperty isRunning = new SimpleBooleanProperty(false);
    private Renames renames;

    public Renamer(Renames renames) {
        this.renames = renames;
    }

    @Override
    public void run() {
        isRunning.set(true);
        isRunning.set(false);
    }

    public SimpleDoubleProperty progressYearProperty() {
        return progressYear;
    }

    public SimpleDoubleProperty progressTransactionProperty() {
        return progressTransaction;
    }

    public SimpleBooleanProperty runningProperty() {
        return isRunning;
    }

}

