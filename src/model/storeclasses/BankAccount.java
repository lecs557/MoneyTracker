package model.storeclasses;

import view.panes.entry_panes.StringEntry;

import java.util.ArrayList;
import java.util.Iterator;

public class BankAccount extends StoreClass {

    private String bankName;
    private String profileId;
    private ArrayList<ArrayList<Transaction>> years_transactions;

    public BankAccount() {
        tableName="BankAccount";
        foreignName = new FieldName("BankAccountId","bankaccount_id","",null);
        fieldNames.add(new FieldName("BankName", "bank_name","TEXT", StringEntry.class));
        foreignKeys.add(new ArrayList<Profile>());
        choiceBoxMethodName="BankName";
    }

    public void setForeignKeysProfile(ArrayList<Profile> profiles){
        ((ArrayList<Profile>) foreignKeys.get(0)).clear();
        ((ArrayList<Profile>) foreignKeys.get(0)).addAll(profiles);
    }

    public void setForeignKeyProfile(Profile profile){
        ((ArrayList<Profile>) getForeignKeys().get(0)).set(0,profile);
    }

    public void processTransactions(ArrayList<Transaction> transactions){
        int year=0;
        ArrayList<Transaction> yearTraList = null;
        for(Transaction curTransaction: transactions){
            if(curTransaction.getLocalDate().getYear()==year){
                yearTraList.add(curTransaction);
            } else {
                year= curTransaction.getLocalDate().getYear();
                years_transactions.add(yearTraList);
                yearTraList=new ArrayList<>();
            }
        }
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


}
