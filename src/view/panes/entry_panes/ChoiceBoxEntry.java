package view.panes.entry_panes;

import controller.DatabaseController;
import controller.ProfileAccountManager;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;
import model.storeclasses.Group;
import model.storeclasses.StoreClass;
import view.panes.EntryPane;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ChoiceBoxEntry extends EntryPane {

    private ChoiceBox<StoreClass> chb = new ChoiceBox<>();

    public ChoiceBoxEntry(String name, Button save, ArrayList<? extends StoreClass> storeClass) {
        super(name, save, storeClass);
        chb.getItems().addAll(storeClass);
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
        if (chb.getSelectionModel().getSelectedItem() == null) {
            return "NULL";
        }
        return (chb.getSelectionModel().getSelectedIndex()+1)+"";
    }

    @Override
    public void setContent(String content) {
        chb.getSelectionModel().select(Integer.parseInt(content)-1);
    }
}
