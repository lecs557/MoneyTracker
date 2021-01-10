package model.storeclasses;

import view.panes.entry_panes.StringEntry;

import java.util.ArrayList;

public class InvoiceFile extends StoreClass{

    private String path;

    public InvoiceFile() {
        tableName="Files";
        fieldNames.add(new FieldName("Path", "path","TEXT", StringEntry.class));
        choiceBoxMethodName="File";
    }

    private String file(){
        String[] temp = path.split("/");
        return temp[temp.length-1];
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
