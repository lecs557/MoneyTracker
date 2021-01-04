package view.panes;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import model.storeclasses.StoreClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public abstract class EntryPane {

    private String name;
    private StoreClass storeClass;

    public EntryPane(String name, Button save, ArrayList<? extends StoreClass> storeClasses) {
        this.storeClass = storeClasses.get(0);
        this.name = name;
        save.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> save());
    }

    public EntryPane(String name, Button save, StoreClass storeClass) {
        this.storeClass = storeClass;
        this.name = name;
        save.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> save());
    }

    public void showContent(){
        try {
            Method getter = storeClass.getClass().getMethod("get"+name);
            String content = (String)getter.invoke(storeClass);
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
            Method setter = storeClass.getClass().getMethod("set"+name, Class.forName("java.lang.String"));
            setter.invoke(storeClass, getContent());
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

    public abstract Region getPane();

    public abstract String getContent();

    public abstract void setContent(String content);
}
