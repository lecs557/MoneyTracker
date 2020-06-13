package controller;

import javafx.beans.property.SimpleBooleanProperty;
import model.Renames;
import model.Transaction;
import model.threads.Renamer;

public class EditController {

    private Renamer rename;
    private SimpleBooleanProperty renameRunning = new SimpleBooleanProperty();

    private Transaction editTransaction;

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

    public Transaction getEditTransaction() {
        return editTransaction;
    }

    public void setEditTransaction(Transaction editTransaction) {
        this.editTransaction = editTransaction;
    }
}
