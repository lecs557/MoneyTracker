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

public abstract class  StoreClassList<T extends StoreClass> extends VBox {

    private final ListView<T> listView;

    public StoreClassList() {
        getStylesheets().add(getClass().getResource("/view/style/sclist.css").toString());
        Button btn_add = new Button("Add");
        btn_add.getStyleClass().add("btn-add");
        listView = new ListView<>();
        listView.getStyleClass().add("list-view");
        listView.setCellFactory(storeClassListView -> new ListCell<T>() {
                @Override
                protected void updateItem(T storeClass, boolean b) {
                super.updateItem(storeClass, b);
                if (storeClass != null && !b) {
                    setGraphic(makeListAnchorPane(storeClass));
                    if(storeClass instanceof Group){
                        setStyle("-fx-background-color: "+((Group)storeClass).getColor()+";");
                    }
                } else {
                    setText("");
                }
            }
        });
        this.getStyleClass().add("store-class-list");

        setListView(SampleClass.getSampleData(getDummy()));

        btn_add.setOnMouseClicked(mouseEvent -> WindowManager.getInstance().openStageOf(new CreateNew<>(getDummy(), false)));
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
            ap_cell.getStyleClass().add("ap-content");
            String labelText = (String) storeClass.getClass().getMethod("get"+storeClass.getChoiceBoxMethodName()).invoke(storeClass);
            Label lbl_itemText = new Label(labelText);
            lbl_itemText.getStyleClass().add("lbl-itemText");
            HBox hb_iconContainer= new HBox();
            hb_iconContainer.getStyleClass().add("hb-iconContainer");
            ImageView iv_edit = new ImageView(new Image(getClass().getResource("/resources/icons/pencil.png").toString()));
            iv_edit.getStyleClass().add("iv-edit");
            iv_edit.setFitHeight(10);
            iv_edit.setPreserveRatio(true);
            Button btn_edit = new Button();
            btn_edit.getStyleClass().add("btn-edit");
            btn_edit.setGraphic(iv_edit);

            hb_iconContainer.getChildren().add(btn_edit);
            ap_cell.getChildren().addAll(lbl_itemText,hb_iconContainer);
            hb_iconContainer.setVisible(false);
            AnchorPane.setLeftAnchor(lbl_itemText,0d);
            AnchorPane.setRightAnchor(hb_iconContainer,0d);

            ap_cell.setOnMouseEntered(mouseEvent -> hb_iconContainer.setVisible(true));
            ap_cell.setOnMouseExited(mouseEvent -> hb_iconContainer.setVisible(false));
            btn_edit.setOnMouseClicked(mouseEvent -> WindowManager.getInstance().openStageOf(new CreateNew<T>(storeClass,true)));

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
