package model.storeclasses;

import view.panes.EntryPane;

import java.util.ArrayList;

public abstract class StoreClass {

    private int id;
    protected String tableName;
    protected String choiceBoxMethodName;
    protected ArrayList<FieldName> fieldNames = new ArrayList<>();
    protected ArrayList<ForeignKey<? extends StoreClass>> foreignKeys = new ArrayList<>();

    public StoreClass() {
        fieldNames.add(FieldName.storeId());
    }

    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getChoiceBoxMethodName() {
        return choiceBoxMethodName;
    }

    public String getTableName() {
        return tableName;
    }

    public ArrayList<FieldName> getFieldNames() {
        return fieldNames;
    }

    public ArrayList<ForeignKey<? extends StoreClass>> getForeignKeys() {
        return foreignKeys;
    }

}
