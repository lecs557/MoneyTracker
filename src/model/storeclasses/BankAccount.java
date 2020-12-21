package model.storeclasses;

import view.panes.entry_panes.StringEntry;

import java.util.ArrayList;

public class BankAccount extends StoreClass {

    private String bankName;
    private ArrayList<ArrayList<Transaction>> years_transactions;

    public BankAccount() {
        tableName="BankAccount";
        fieldNames.add(new FieldName("BankName", "bank_name","TEXT", StringEntry.class));
        foreignKeys.add(new ForeignKey<>("profile_id", new Profile()));
    }

    public void setForeignKeysProfile(ArrayList<Profile> profiles){
        ((ForeignKey<Profile>)getForeignKeys().get(0)).setForeigns(profiles);
    }

    public void setForeignKeyProfile(Profile profile){
        ((ForeignKey<Profile>)getForeignKeys().get(0)).getForeigns().set(0,profile);
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
