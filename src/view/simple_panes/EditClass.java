package view.simple_panes;

import controller.DatabaseController;
import controller.ViewController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Main;
import model.storeclasses.FieldName;
import model.storeclasses.StoreClass;
import view.panes.EntryPane;
import view.panes.entry_panes.ChoiceBoxEntry;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class EditClass<T extends StoreClass> extends HBox {

    private VBox vb_origin;
    private VBox vb_change;
    private Button btn_save;

    private T storeClass;

    public EditClass(T storeCls){
        storeClass=storeCls;
        vb_origin = new VBox();
        vb_origin.setSpacing(15);
        vb_change = new VBox();
        vb_change.setSpacing(15);
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
                vb_origin.getChildren().add(hbox);
            }
        }
        for (ArrayList<? extends StoreClass> foreignKey : storeClass.getForeignKeys()) {
            HBox hbox = new HBox();
            Label label = new Label(foreignKey.get(0).getClass().getSimpleName());
            label.setPrefWidth(190);
            EntryPane entryPane = new ChoiceBoxEntry("Id", btn_save, foreignKey);
            hbox.getChildren().addAll(label, entryPane.getPane());
            vb_origin.getChildren().add(hbox);
        }


        btn_save.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent ->{
            DatabaseController.storeObject(storeClass);
            ViewController.refresh();
            Main.secStage.close();
        });
        vb_origin.getChildren().add(btn_save);
        getChildren().add(lbl_header);
        getChildren().add(vb_origin);
    }
}
