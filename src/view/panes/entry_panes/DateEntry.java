package view.panes.entry_panes;

import javafx.scene.control.Button;
import model.storeclasses.StoreClass;
import view.panes.EntryPane;

public class DateEntry extends EntryPane {


    public DateEntry(String name, Button save, StoreClass storeClass) {
        super(name, save, storeClass);
        setMyNodePath("/view/panes/DateEntry");
    }
}

