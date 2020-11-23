package model.threads;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.Profile;
import model.Group;
import model.Main;
import model.Transaction;
import view.simple_panes.ViewUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Saver extends Thread {

    private SimpleDoubleProperty progressYear = new SimpleDoubleProperty();
    private SimpleDoubleProperty progressTransaction = new SimpleDoubleProperty();
    private SimpleBooleanProperty running = new SimpleBooleanProperty();

    @Override
    public void run() {
        running.set(true);
        running.set(false);
    }


    public SimpleDoubleProperty progressYearProperty() {
        return progressYear;
    }

    public SimpleDoubleProperty progressTransactionProperty() {
        return progressTransaction;
    }

    public SimpleBooleanProperty runningProperty() {
        return running;
    }
}
