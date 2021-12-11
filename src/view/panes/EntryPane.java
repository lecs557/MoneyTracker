package view.panes;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import model.storeclasses.StoreClass;
import view.panes.entry_panes.ChoiceBoxEntry;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public abstract class EntryPane {

    protected MyNode myNode;
    private final String name;
    private final StoreClass storeClass;

    public EntryPane(String name, Button save, StoreClass storeClass) {
        this.storeClass = storeClass;
        this.name = name;
        save.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> save());
    }

    public void showContent(){
        try {
            Method getter = storeClass.getClass().getMethod("get"+name);
            String content = getter.invoke(storeClass)+"";


            setContent(content);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void save(){
        try {
            Method setter;
            if(this instanceof ChoiceBoxEntry){
                setter = storeClass.getClass().getMethod("set" + name, int.class);
                setter.invoke(storeClass, Integer.parseInt(getContent()));
            } else {
                setter = storeClass.getClass().getMethod("set" + name, Class.forName("java.lang.String"));
                setter.invoke(storeClass, getContent());
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public Region getPane() {
        System.out.println(myNode);
        return myNode;
    }

    public String getContent() {
        return myNode.getContent();
    }

    public void setContent(String content) {
        myNode.setContent(content);
    }
}
