package model.storeclasses;

import model.FieldName;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Group extends StoreClass {

   int id;
   String groupName;
   int sum;


    @Override
    public String getTableName() {
        return "Groups";
    }

    @Override
    public ArrayList<FieldName> getFieldNames() {
        ArrayList<FieldName> fieldNames = new ArrayList<FieldName>();
        fieldNames.add(new FieldName("Id", "id","INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT"));
        fieldNames.add(new FieldName("GroupName", "group_name","TEXT"));
        fieldNames.add(new FieldName("Sum", "sum","TEXT"));
        return fieldNames;
    }
}
