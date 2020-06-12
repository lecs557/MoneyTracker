package controller;

import javafx.beans.property.SimpleBooleanProperty;
import model.Renames;
import model.threads.Renamer;

public class EditController {

    Renamer rename;
    private SimpleBooleanProperty renameRunning = new SimpleBooleanProperty();

    public void rename(Renames a){
        rename = new Renamer(a);
        rename.start();
        renameRunning.unbind();
        renameRunning.bind(rename.isRunningProperty());

    }

    public Renamer getRename() {
        return rename;
    }

    public SimpleBooleanProperty renameRunningProperty() {
        return renameRunning;
    }
}
