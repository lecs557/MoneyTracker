package view.simple_panes;

import controller.WindowManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.storeclasses.Group;
import model.storeclasses.StoreClass;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public abstract class StoreClassList<T extends StoreClass> extends VBox {

    private final ListView<T> listView;

    public StoreClassList() {
        getStylesheets().add("view/style/sclist.css");
        Button btn_add = new Button("Add");
        btn_add.getStyleClass().add("btn-add");
        listView = new ListView<>();
        listView.setCellFactory(storeClassListView -> new ListCell<T>() {
            @Override
            protected void updateItem(T storeClass, boolean b) {
            super.updateItem(storeClass, b);
            if (storeClass != null) {
                setGraphic(makeListAnchorPane(storeClass));
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

    private <T extends StoreClass> AnchorPane makeListAnchorPane(T storeClass){
        try {
            AnchorPane ap_cell = new AnchorPane();
            ap_cell.getStyleClass().add("ap-cell");
            String labelText = (String) storeClass.getClass().getMethod("get"+storeClass.getChoiceBoxMethodName()).invoke(storeClass);
            Label lbl_itemText = new Label(labelText);
            lbl_itemText.getStyleClass().add("lbl-itemText");
            HBox  hb_iconContainer= new HBox();
            hb_iconContainer.getStyleClass().add("hb-iconContainer");
            ImageView iv_edit = new ImageView(new Image("/ressources/icons/pencil.png"));
            iv_edit.getStyleClass().add("iv-edit");
            Button btn_edit = new Button();
            btn_edit.getStyleClass().add("btn-edit");
            btn_edit.setGraphic(iv_edit);
            ImageView iv_garbage = new ImageView(new Image("/ressources/icons/garbage.png"));
            iv_garbage.getStyleClass().add("iv-garbage");
            Button btn_garbage = new Button();
            btn_garbage.getStyleClass().add("btn-garbage");
            btn_garbage.setGraphic(iv_garbage);

            hb_iconContainer.getChildren().addAll(iv_edit,iv_garbage);
            ap_cell.getChildren().addAll(lbl_itemText,hb_iconContainer);
            hb_iconContainer.setVisible(false);
            AnchorPane.setLeftAnchor(lbl_itemText,0d);
            AnchorPane.setRightAnchor(hb_iconContainer,0d);

            ap_cell.setOnMouseEntered(mouseEvent -> hb_iconContainer.setVisible(true));
            ap_cell.setOnMouseExited(mouseEvent -> hb_iconContainer.setVisible(false));
            iv_edit.setOnMouseClicked(mouseEvent -> WindowManager.openStageOf(new CreateNew<T>(storeClass,true)));
            iv_garbage.setOnMouseClicked(mouseEvent -> WindowManager.openStageOf(new CreateNew<T>(storeClass,true)));

            return ap_cell;

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
