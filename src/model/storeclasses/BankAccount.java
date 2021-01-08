package model.storeclasses;

import view.panes.entry_panes.StringEntry;

import java.util.ArrayList;

public class BankAccount extends StoreClass {

    private String bankName;
    private String profileId;
    private ArrayList<ArrayList<Transaction>> years_transactions;

    public BankAccount() {
        tableName="BankAccount";
        fieldNames.add(new FieldName("BankName", "bank_name","TEXT", StringEntry.class));
        foreignKeys.add(new ForeignKey<Profile>("ProfileId","profile_id", new Profile()));
        choiceBoxMethodName="BankName";
    }

    public void setForeignKeysProfile(ArrayList<Profile> profiles){
        ((ForeignKey<Profile>) foreignKeys.get(0)).setForeignObjects(profiles);
    }

    public void setForeignKeyProfile(Profile profile){
        ((ForeignKey<Profile>) foreignKeys.get(0)).getForeignObjects().clear();
        ((ForeignKey<Profile>) foreignKeys.get(0)).getForeignObjects().add(profile);
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

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
}
