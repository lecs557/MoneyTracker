package view.simple_panes;

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
import java.time.LocalDate;

public class EntryPane extends Region {

    private String name;
    private MyNode myNode;

    public EntryPane(String name, Button save, StoreClass storeClass) {
        this.name = name;
        if (name.equals("Date")) {
            myNode = new MyNode();
            myNode.setPath("/view/panes/EnterTransaction");
        }

        save.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> System.out.println(name));
    }

    public Region getPane(){
        if (myNode != null) {
            return myNode;
        }
        return new TextField();
    }
}
