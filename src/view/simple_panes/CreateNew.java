package view.simple_panes;

import controller.DatabaseController;
import controller.ProfileAccountManager;
import controller.ViewController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Main;
import model.storeclasses.BankAccount;
import model.storeclasses.FieldName;
import model.storeclasses.ForeignKey;
import model.storeclasses.StoreClass;
import view.panes.EntryPane;
import view.panes.entry_panes.ChoiceBoxEntry;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class CreateNew<T extends StoreClass> extends VBox {

    private VBox vb_fields;
    private Button btn_save;
    private Label lbl_header;

    private T storeClass;
    private boolean edit;

    public CreateNew(T storeCls, boolean edit){
        storeClass=storeCls;
        this.edit = edit;
        vb_fields = new VBox();
        vb_fields.setSpacing(15);
        btn_save = new Button("SAVE");
        addStoreClassFields();
    }

    private void addStoreClassFields() {
        if (edit) {
            lbl_header = new Label("Bearbeite: " + storeClass.getTableName());
        } else {
            lbl_header = new Label("Erstelle: " + storeClass.getTableName());
        }
        lbl_header.getStyleClass().add("lbl_header");
        for(Field field: storeClass.getClass().getClasses()[1].getFields()){
            FieldName name = null;
            try {
                name = (FieldName) field.get(storeClass);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (!name.getProgramName().equals("Id")) {
                HBox hbox = new HBox();
                Label label = new Label(name.getProgramName());
                label.setPrefWidth(190);
                try {
                    Constructor<? extends EntryPane> constructor = name.getEntryClass().getDeclaredConstructor(String.class, Button.class, StoreClass.class);
                    EntryPane entryPane = constructor.newInstance(name.getProgramName(), btn_save, storeClass);
                    hbox.getChildren().addAll(label, entryPane.getPane());
                    if(edit){
                        entryPane.showContent();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                vb_fields.getChildren().add(hbox);
            }
        }
        for(Field field: storeClass.getClass().getClasses()[0].getFields()){
            ForeignKey<? extends StoreClass> key = null;
            try {
                key = (ForeignKey<? extends StoreClass>) field.get(storeClass);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(!key.getForeignObjects().isEmpty()) {
                HBox hbox = new HBox();
                Label label = new Label(key.getDummyClazz().getClass().getSimpleName());
                label.setPrefWidth(190);
                EntryPane entryPane = new ChoiceBoxEntry("Id", btn_save, key.getForeignObjects());
                hbox.getChildren().addAll(label, entryPane.getPane());
                if (edit) {
                    entryPane.showContent();
                }
                vb_fields.getChildren().add(hbox);
            }
        }


        btn_save.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent ->{
            if(edit){
                DatabaseController.updateObject(storeClass);
            } else {
               DatabaseController.storeObject(storeClass, false);
            }
            if (storeClass instanceof BankAccount){
                ProfileAccountManager.add((BankAccount) storeClass);
            }
            ViewController.refresh();
            Main.secStage.close();
        });
        vb_fields.getChildren().add(btn_save);
        getChildren().add(lbl_header);
        getChildren().add(vb_fields);
    }
}
