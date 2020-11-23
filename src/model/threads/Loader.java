package model.threads;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import model.Profile;
import model.Group;
import model.Main;
import model.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Loader extends Thread {

    private String name;
    private String path;
    private SimpleBooleanProperty running = new SimpleBooleanProperty();

    public Loader(String name, String path) {
        this.name = name;
        this.path = path;
    }

    @Override
    public void run() {
        running.set(true);

        running.set(false);

    }

    public SimpleBooleanProperty isRunningProperty() {
        return running;
    }
}
