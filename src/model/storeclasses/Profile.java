package model.storeclasses;

import view.panes.ItemRegion;
import view.panes.entry_panes.StringEntry;

import java.util.ArrayList;

public class Profile extends StoreClass {

    private String name;

    public Profile() {
        tableName = "Profiles";
        choiceBoxMethodName="Name";
    }

    public static class Variables {
        public static FieldName id = FieldName.storeId();
        public static FieldName name = new FieldName("Name", "name","TEXT", StringEntry.class);
    }

    public static class ForeignKeys{

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

