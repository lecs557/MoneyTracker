package view.panes.entry_panes;

import controller.ProfileAccountManager;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.storeclasses.InvoiceFile;
import model.storeclasses.StoreClass;
import view.panes.EntryPane;
import view.simple_panes.PaneUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MultipleChoiceBoxEntry extends EntryPane {

    private final StoreClass storeclass;
    private String fileIds;

    public MultipleChoiceBoxEntry(String name, StoreClass storeClass) {
        super(name, storeClass);
        this.storeclass=storeClass;
        Button add = new Button("ADD");
        getChildren().addAll(add);

        add.setOnMouseClicked(mouseEvent -> {
            ChoiceBox<StoreClass> chb = new ChoiceBox<>();
            if(name.contains("Invoice")) {
                chb.getItems().addAll(ProfileAccountManager.getInstance().getProfilesInvoiceFiles());
            }
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
            getChildren().add(0, chb);
        });
    }

    @Override
    public String getContent() {
        String result="";
        for (Node chb:getChildren()) {
            if(chb instanceof ChoiceBox) {
                StoreClass storeClazz = ((ChoiceBox<StoreClass>) chb).getSelectionModel().getSelectedItem();
                if (storeClazz != null) {
                    result += storeClazz.getId() + ";";
                }
            }
        }
        if(result.isEmpty()){
            return 0+"";
        }
        return result.substring(0,result.length()-1);
    }

    @Override
    public void setContent(String content) {
        String[] temp = content.split(";");
        for(String id: temp){
            try {
                ChoiceBox<StoreClass> chb = new ChoiceBox<>();
                chb.getSelectionModel().select(ProfileAccountManager.getInstance().getById(storeclass, Integer.parseInt(id)));
            }catch (Exception e){
                System.out.println("PARSEERROR");
            }
        }
    }
}
