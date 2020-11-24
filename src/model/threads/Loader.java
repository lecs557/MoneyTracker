package model.threads;

import javafx.beans.property.SimpleBooleanProperty;

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
