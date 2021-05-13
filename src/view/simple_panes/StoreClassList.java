package view.simple_panes;

import controller.WindowManager;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import model.storeclasses.Group;
import model.storeclasses.StoreClass;

import java.util.ArrayList;

public abstract class StoreClassList<T extends StoreClass> extends VBox {

    private final ListView<T> listView;

    public StoreClassList() {
        Button btn_add = new Button("Add");
        listView = new ListView<>();
        listView.setCellFactory(storeClassListView -> new ListCell<T>() {
            @Override
            protected void updateItem(T storeClass, boolean b) {
            super.updateItem(storeClass, b);
            if (storeClass != null) {
                setGraphic(PaneUtils.makeListAnchorPane(storeClass));
                if(storeClass instanceof Group){
                    setStyle("-fx-background-color: "+((Group)storeClass).getColor()+";");
                }
            } else {
                setText("");
            }
            }
        });

        setListView(SampleClass.getSampleData(getDummy()));

        btn_add.setOnMouseClicked(mouseEvent -> WindowManager.openStageOf(new CreateNew<>(getDummy(), false)));
        getChildren().addAll(btn_add,listView);
    }

    public abstract T getDummy();

    private void setListView(ArrayList<T> storClasses) {
        listView.getItems().addAll(storClasses);
    }

    public ListView<T> getListView() {
        return listView;
    }
}
