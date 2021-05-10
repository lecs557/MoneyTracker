package view.simple_panes;

import controller.WindowManager;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import model.storeclasses.BankAccount;
import model.storeclasses.Group;

import java.util.ArrayList;

public class GroupList extends StoreClassList<Group> {

    @Override
    public Group getDummy() {
        return new Group();
    }
}
