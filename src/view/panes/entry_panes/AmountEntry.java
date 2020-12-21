package view.panes.entry_panes;

import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import model.PathStore;
import model.storeclasses.StoreClass;
import view.panes.EntryPane;
import view.panes.MyNode;

public class AmountEntry extends EntryPane {

    private MyNode myNode = new MyNode(PathStore.ENTRYPANE+"AmountEntry");

    public AmountEntry(String name, Button save, StoreClass storeClass) {
        super(name, save, storeClass);
    }

    @Override
    public Region getPane() {
        return myNode;
    }

    @Override
    public String getContent() {
        return myNode.getContent();
    }
}
