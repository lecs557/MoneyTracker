package view.panes;

import javafx.scene.control.Button;
import model.storeclasses.StoreClass;

public class AmountEntry extends EntryPane {
    public AmountEntry(String name, Button save, StoreClass storeClass) {
        super(name, save, storeClass);
    }
}
