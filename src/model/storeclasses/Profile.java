package model.storeclasses;

import model.BankAccount;
import model.FieldName;
import model.storeclasses.StoreClass;

import java.util.ArrayList;

public class Profile extends StoreClass {

    private int id;
    private String name;
    private ArrayList<BankAccount> bankAccounts;

    public Profile()  {

    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getTableName() {
        return "Profiles";
    }

    @Override
    public ArrayList<FieldName> getFieldNames() {
        ArrayList<FieldName> fieldNames = new ArrayList<FieldName>();
        fieldNames.add(new FieldName("Id", "id","INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT"));
        fieldNames.add(new FieldName("Name", "name","TEXT"));
        return fieldNames;
    }
}

