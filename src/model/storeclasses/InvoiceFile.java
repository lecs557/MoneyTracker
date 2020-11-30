package model.storeclasses;

import java.util.ArrayList;

public class InvoiceFile extends StoreClass{

    private int id;
    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getTableName() {
        return "Invoice_Files";
    }

    @Override
    public ArrayList<FieldName> getFieldNames() {
        ArrayList<FieldName> fieldNames = new ArrayList<FieldName>();
        fieldNames.add(new FieldName("Id", "id","INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT"));
        fieldNames.add(new FieldName("Path", "path","TEXT"));
        return fieldNames;
    }
}
