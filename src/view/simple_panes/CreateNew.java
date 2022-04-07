package view.simple_panes;

import controller.DatabaseController;
import controller.ProfileAccountManager;
import controller.ViewController;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.AppStart;
import model.storeclasses.*;
import view.panes.EntryPane;
import view.panes.entry_panes.ChoiceBoxEntry;
import view.panes.entry_panes.MultipleChoiceBoxEntry;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class CreateNew<T extends StoreClass> extends VBox {

    private final VBox vb_fields;
    private final Button btn_save;
    private final Button btn_delete;

    private final T storeClass;
    private final boolean edit;

    public CreateNew(T storeCls, boolean edit){
        storeClass=storeCls;
        this.edit = edit;
        vb_fields = new VBox();
        vb_fields.setSpacing(15);
        btn_save = new Button("SAVE");
        btn_delete = new Button("DELETE");
        addStoreClassFields();
    }

    private void addStoreClassFields() {
        Label lbl_header;
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
                    Class<? extends EntryPane> entry = name.getEntryClass();
                    Constructor<? extends EntryPane> constructor= entry.getDeclaredConstructor(String.class, StoreClass.class);;
                    EntryPane entryPane = constructor.newInstance(name.getProgramName(), storeClass);;
                    hbox.getChildren().addAll(label, entryPane);
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
                EntryPane entryPane = new ChoiceBoxEntry(key.getProgramName(), storeClass, key.getForeignObjects());
                hbox.getChildren().addAll(label, entryPane);
                if (edit) {
                    entryPane.showContent();
                }
                vb_fields.getChildren().add(hbox);
            }
        }
        addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                submit();
            }
        });
        btn_save.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> submit());
        btn_delete.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> delete());
        HBox hb_buttons = new HBox();
        hb_buttons.getChildren().add(btn_save);
        if(edit){
            hb_buttons.getChildren().add(btn_delete);
        }
        vb_fields.getChildren().add(hb_buttons);
        getChildren().add(lbl_header);
        getChildren().add(vb_fields);
    }

    private void submit() {
        for (Node node: vb_fields.getChildren()){
            if (node instanceof HBox) {
                if (((HBox) node).getChildren().size() > 1) {
                    Node entry = ((HBox) node).getChildren().get(1);
                    if (entry instanceof EntryPane) {
                        if (!((EntryPane) entry).save()) {
                            return;
                        }
                    }
                }
            }
        }
        if(edit){
            DatabaseController.getInstance().updateObject(storeClass);
        } else {
            DatabaseController.getInstance().storeObject(storeClass, false);
        }
        ViewController.getInstance().refresh(storeClass);
        AppStart.secStage.close();
    }

    private void delete(){
        DatabaseController.getInstance().deleteObject(storeClass);
        ViewController.getInstance().refresh(storeClass);
        AppStart.secStage.close();
    }
}
