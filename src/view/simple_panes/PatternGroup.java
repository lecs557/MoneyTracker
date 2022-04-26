package view.simple_panes;

import controller.ProfileAccountManager;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.storeclasses.Group;


public class PatternGroup extends VBox {

    public PatternGroup() {
        HBox hb_pattern = new HBox();
        Label lbl_pattern = new Label("Pattern");
        TextField tf_pattern = new TextField();
        ComboBox<Group> cb_groups = new ComboBox<>();
        cb_groups.getItems().addAll(ProfileAccountManager.getInstance().getProfilesGroups());
        cb_groups.setConverter(new StringConverter<Group>() {
            @Override
            public String toString(Group group) {
                if (group != null) {
                    return group.getGroupName();
                }
                return "";
            }
            @Override
            public Group fromString(String s) {
                return null;
            }
        });
        PaneUtils.selectFirst(cb_groups);
        Button btn_submit = new Button("OK");
        hb_pattern.getChildren().addAll(lbl_pattern,tf_pattern);
        getChildren().addAll(hb_pattern,cb_groups,btn_submit);
    }
}
