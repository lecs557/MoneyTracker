package view.panes;

import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import model.storeclasses.StoreClass;

public interface ItemRegion {

     ObservableList<? extends StoreClass> getItems();
}
