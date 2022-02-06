package view.panes.entry_panes;

import model.PathStore;
import model.storeclasses.StoreClass;
import view.panes.EntryPane;
import view.panes.MyNode;

public class AmountEntry extends EntryPane {

    private final MyNode myNode = new MyNode(PathStore.ENTRYPANE+"AmountEntry");

    public AmountEntry(String name, StoreClass storeClass) {
        super(name, storeClass);
        getChildren().add(myNode);
    }

    @Override
    public String getContent() {
        return myNode.getContent();
    }

    @Override
    public void setContent(String content) {
        myNode.setContent(content);
    }
}
