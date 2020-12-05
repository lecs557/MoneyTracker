package view.panes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import model.storeclasses.StoreClass;
import view.panes.MyNode;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

public abstract class EntryPane {

    private String name;
    private MyNode myNode;
    private StoreClass storeClass;

    public EntryPane(String name, Button save, StoreClass storeClass) {
        this.storeClass = storeClass;
        this.name = name;
        save.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> save());
    }

    public void save(){
        try {
            Method setter = storeClass.getClass().getMethod("set"+name, Class.forName("java.lang.String"));
            if (myNode != null) {
                setter.invoke(storeClass, myNode.getContent());
            } else {
                setter.invoke(storeClass, myNode.getContent());
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

    public Region getPane(){
        if (myNode != null) {
            return myNode;
        }
        return new TextField();
    }

    public void setMyNode(MyNode myNode) {
        this.myNode = myNode;
    }
}
