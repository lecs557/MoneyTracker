package view.simple_panes;

import controller.DatabaseController;
import controller.ViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.Main;
import model.storeclasses.FieldName;
import model.storeclasses.StoreClass;
import view.panes.EntryPane;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CreateNew<T extends StoreClass> extends VBox {

    private VBox vb_fields;
    private Button btn_save;

    private T storeClass;

    public CreateNew(T storeCls){
        storeClass=storeCls;
        vb_fields = new VBox();
        vb_fields.setSpacing(15);
        btn_save = new Button("SAVE");
        addStoreClassFields();
    }

    private void addStoreClassFields() {
        Label lbl_header = new Label("Erstelle: " + storeClass.getTableName());
        lbl_header.getStyleClass().add("lbl_header");
        for (FieldName name : storeClass.getFieldNames()) {
            if (!name.getProgramName().equals("Id")) {
                HBox hbox = new HBox();
                Label label = new Label(name.getProgramName());
                label.setPrefWidth(190);
                Constructor<? extends EntryPane> constructor = null;
                try {
                    constructor = name.getEntryClass().getDeclaredConstructor(String.class, Button.class, StoreClass.class);
                    EntryPane entryPane = constructor.newInstance(name.getProgramName(), btn_save, storeClass);
                    hbox.getChildren().addAll(label, entryPane.getPane());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                vb_fields.getChildren().add(hbox);
            }
        }
        btn_save.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent ->{
            DatabaseController.storeObject(storeClass);
            ViewController.refresh();
            Main.secStage.close();
        });
        vb_fields.getChildren().add(btn_save);
        getChildren().add(lbl_header);
        getChildren().add(vb_fields);
    }
}
