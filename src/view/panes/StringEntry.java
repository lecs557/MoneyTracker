package view.panes;

import javafx.scene.control.Button;
import model.storeclasses.StoreClass;

public class StringEntry extends EntryPane {
    public StringEntry(String name, Button save, StoreClass storeClass) {
        super(name, save, storeClass);
    }
}
