package view.simple_panes;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.storeclasses.FieldName;
import model.storeclasses.StoreClass;
import view.panes.EntryPane;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CreateNew extends Region {

    private StoreClass storeClass;
    private SimpleStringProperty className = new SimpleStringProperty("model.storeclasses.Profile");

    public CreateNew() {
        try {
            storeClass = (StoreClass) Class.forName(classNameProperty().get()).getDeclaredConstructor().newInstance();
            getChildren().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        VBox vBox = new VBox();
        Button save = new Button("SAVE");
        vBox.setSpacing(15);
        for (FieldName name : storeClass.getFieldNames()) {
            if (!name.getProgramName().equals("Id")) {
                HBox hbox = new HBox();
                Label label = new Label(name.getProgramName());
                label.setPrefWidth(190);
                Constructor<? extends EntryPane> constructor = null;
                try {
                    constructor = name.getEntryClass().getDeclaredConstructor(String.class, Button.class, StoreClass.class);
                    EntryPane entryPane = constructor.newInstance(name.getProgramName(), save, storeClass);
                    hbox.getChildren().addAll(label, entryPane.getPane());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                vBox.getChildren().add(hbox);
            }
        }
        vBox.getChildren().add(save);
        getChildren().add(vBox);

        className.addListener((observableValue, s, t1) -> {
            try {
                storeClass = (StoreClass) Class.forName(classNameProperty().get()).getDeclaredConstructor().newInstance();
                getChildren().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
            vBox.getChildren().clear();
            vBox.setSpacing(15);
            for (FieldName name : storeClass.getFieldNames()) {
                if (!name.getProgramName().equals("Id")) {
                    HBox hbox = new HBox();
                    Label label = new Label(name.getProgramName());
                    label.setPrefWidth(190);
                    Constructor<? extends EntryPane> constructor = null;
                    try {
                        constructor = name.getEntryClass().getDeclaredConstructor(String.class, Button.class, StoreClass.class);
                        EntryPane entryPane = constructor.newInstance(name.getProgramName(), save, storeClass);
                        hbox.getChildren().addAll(label, entryPane.getPane());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    vBox.getChildren().add(hbox);
                }
            }
            vBox.getChildren().add(save);
            getChildren().add(vBox);
        });
    }


    public SimpleStringProperty classNameProperty() {
        return className;
    }

    public void setClassName(String className) {
        this.className.set(className);
    }
}
