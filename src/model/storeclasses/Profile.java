package model.storeclasses;

import view.panes.ItemRegion;
import view.panes.entry_panes.StringEntry;

import java.util.ArrayList;

public class Profile extends StoreClass {

    private String name;
    private static ItemRegion itemRegion;
    private ArrayList<BankAccount> bankAccounts;
    private ArrayList<Group> groups;

    public Profile() {
        tableName = "Profiles";
        fieldNames.add(new FieldName("Name", "name","TEXT", StringEntry.class));
        choiceBoxMethodName="Name";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ItemRegion getItemRegion() {
        return itemRegion;
    }

    public static void setItemRegion(ItemRegion itemRegion) {
        Profile.itemRegion = itemRegion;
    }

    public void setBankAccounts(ArrayList<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public ArrayList<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void addBankAccount(BankAccount bankAccount){
        bankAccounts.add(bankAccount);
    }
}

