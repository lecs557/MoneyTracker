package model.storeclasses;

import controller.ProfileAccountManager;
import view.panes.entry_panes.StringEntry;

import java.util.ArrayList;

public class Profile extends StoreClass {

    private String name;
    private ArrayList<BankAccount> bankAccounts;

    public Profile() {
        tableName = "Profiles";
        choiceBoxMethodName="Name";
        fieldNames.add(new FieldName("Name", "name","TEXT", StringEntry.class));
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void addBankAccount(BankAccount bankAccount){
        bankAccounts.add(bankAccount);
    }
}

