package view.simple_panes;

import model.storeclasses.Group;

public class GroupList extends StoreClassList<Group> {

    public GroupList() {
        super();
        this.getStyleClass().add("group");
    }

    @Override
    public Group getDummy() {
        return new Group();
    }
}
