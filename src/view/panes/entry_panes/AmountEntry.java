package view.panes.entry_panes;

import javafx.scene.control.Button;
import model.PathStore;
import model.storeclasses.StoreClass;
import view.panes.EntryPane;

public class AmountEntry extends EntryPane {
    public AmountEntry(String name, Button save, StoreClass storeClass) {
        super(name, save, storeClass);
        setMyNodePath(PathStore.ENTRYPANE+"AmountEntry");

    }
}
