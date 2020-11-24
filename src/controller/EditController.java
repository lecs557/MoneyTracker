package controller;

import javafx.beans.property.SimpleBooleanProperty;
import model.Renames;
import model.storeclasses.Transaction;
import model.threads.Renamer;

public class EditController {

    private Renamer renamer;
    private SimpleBooleanProperty renameRunning = new SimpleBooleanProperty();

    private Transaction editTransaction;

    public void startRenamer(Renames a){
        renamer = new Renamer(a);
        renamer.start();
        renameRunning.unbind();
        renameRunning.bind(renamer.runningProperty());

    }

    public Renamer getRenamer() {
        return renamer;
    }

    public SimpleBooleanProperty renameRunningProperty() {
        return renameRunning;
    }

    public Transaction getEditTransaction() {
        return editTransaction;
    }

    public void setEditTransaction(Transaction editTransaction) {
        this.editTransaction = editTransaction;
    }
}
