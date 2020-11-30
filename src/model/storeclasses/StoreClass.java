package model.storeclasses;

import java.util.ArrayList;

public abstract class StoreClass {

    private String tableName;
    private ArrayList<FieldName> fieldNames;
    private ArrayList<ForeignKey<? extends StoreClass>> foreignKeys;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ArrayList<FieldName> getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(ArrayList<FieldName> fieldNames) {
        this.fieldNames = fieldNames;
    }

    public ArrayList<ForeignKey<? extends StoreClass>> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(ArrayList<ForeignKey<? extends StoreClass>> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

}
