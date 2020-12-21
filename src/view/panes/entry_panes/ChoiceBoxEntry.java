package view.panes.entry_panes;

import controller.DatabaseController;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;
import model.storeclasses.Group;
import model.storeclasses.StoreClass;
import view.panes.EntryPane;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ChoiceBoxEntry extends EntryPane {

    ChoiceBox<StoreClass> chb = new ChoiceBox<>();

    public ChoiceBoxEntry(String name, Button save, StoreClass storeClass) {
        super(name, save, storeClass);
        chb.getItems().addAll(DatabaseController.computeStoreClasses(storeClass));
        chb.setConverter(new StringConverter<StoreClass>() {
            @Override
            public String toString(StoreClass storeClass) {
                try {
                    if (storeClass != null) {
                        Method method = storeClass.getClass().getMethod("get" + storeClass.getChoiceBoxMethodName());
                        return (String) method.invoke(storeClass);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "None";
            }

            @Override
            public StoreClass fromString(String s) {
                return null;
            }
        });
    }

    @Override
    public Region getPane() {
        return chb;
    }

    @Override
    public String getContent() {
        return (chb.getSelectionModel().getSelectedIndex()+1)+"";
    }
}
