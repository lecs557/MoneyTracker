package model.storeclasses;

import java.util.ArrayList;

public class Profile extends StoreClass {

    private int id;
    private String name;
    private ArrayList<BankAccount> bankAccounts;

    public Profile() {
        setTableName("Profiles");
        ArrayList<FieldName> fieldNames = new ArrayList<FieldName>();
        fieldNames.add(FieldName.storeId());
        fieldNames.add(new FieldName("Name", "name","TEXT"));
        setFieldNames(fieldNames);
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

}
