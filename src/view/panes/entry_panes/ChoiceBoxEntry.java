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

    public ChoiceBoxEntry(String name, Button save, StoreClass storeClass, ArrayList<? extends StoreClass> storeClasses) {
        super(name, save, storeClass);

        if(name.contains("Group")){
            Group groupNull = new Group();
            groupNull.setGroupName("Keine Gruppe");
            groupNull.setId(0);
            chb.getItems().add(groupNull);
        }
        chb.getItems().addAll(storeClasses);
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
        if (name.contains("profile")){
            chb.getSelectionModel().select(ProfileAccountManager.getCurrentAccount().getId()-1);
        } else{
            chb.getSelectionModel().select(0);
        }

    }

    @Override
    public Region getPane() {
        return chb;
    }

    @Override
    public String getContent() {
        StoreClass item = chb.getSelectionModel().getSelectedItem();
        if (item == null) {
            return 0+"";
        }
        if(item instanceof Group){
            return chb.getSelectionModel().getSelectedItem().getId()+"";
        }
        return chb.getSelectionModel().getSelectedItem().getId()+"";
    }

    @Override
    public void setContent(String content) {
        chb.getSelectionModel().select(Integer.parseInt(content)-1);
    }
}
