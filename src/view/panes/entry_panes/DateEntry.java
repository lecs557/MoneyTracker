package view.panes.entry_panes;

import model.PathStore;
import model.storeclasses.StoreClass;

public class DateEntry extends EntryPane {

    private final MyNode myNode = new MyNode(PathStore.ENTRYPANE+"DateEntry");

    public DateEntry(String name, StoreClass storeClass) {
        super(name, storeClass);
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

