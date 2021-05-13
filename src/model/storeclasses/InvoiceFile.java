package model.storeclasses;

import view.panes.entry_panes.StringEntry;

import java.util.ArrayList;

public class InvoiceFile extends StoreClass{

    private String path;
    private String name="FILE";


    public InvoiceFile() {
        tableName="InvoiceFiles";
        choiceBoxMethodName="Name";
    }

    public static class Variables {
        public static FieldName id =FieldName.storeId();
        public static FieldName path = new FieldName("Path", "path","TEXT", StringEntry.class);
    }

    public static class ForeignKeys{

    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
