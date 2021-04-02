package view.simple_panes;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import model.storeclasses.Group;

import java.util.ArrayList;

public class GroupList extends VBox {

    private Button btn_add;
    public ListView<Group> listView;

    public GroupList() {
        btn_add = new Button("Add");
        listView = new ListView<>();
        listView.setCellFactory(groupListView -> {
            ListCell<Group> cell = new ListCell<>() {
                @Override
                protected void updateItem(Group group, boolean b) {
                    super.updateItem(group, b);
                    if (group != null) {
                        setText(group.getGroupName());
                        setStyle("-fx-background-color: "+group.getColor()+";");
                    } else {
                        setText("");
                    }
                }
            };
            return cell;
        });
        setGroups(SampleClass.getSampleGroups());
        getChildren().addAll(btn_add,listView);

    }

    private void setGroups(ArrayList<Group> groups) {
        listView.getItems().addAll(groups);
    }
}
