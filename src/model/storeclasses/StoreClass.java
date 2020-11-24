package model.storeclasses;

import model.FieldName;

import java.util.ArrayList;

public abstract class StoreClass {

    public abstract String getTableName();

    public abstract ArrayList<FieldName> getFieldNames();
}
