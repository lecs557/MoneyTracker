package model.storeclasses;

import view.panes.entry_panes.StringEntry;

import java.util.ArrayList;

public class BankAccount extends StoreClass {

    private String bankName;
    private String profileId;

    public BankAccount() {
        tableName="BankAccount";
        choiceBoxMethodName="BankName";
    }

    public static class Variables {
        public static FieldName id = FieldName.storeId();
        public static FieldName bank_name = new FieldName("BankName", "bank_name","TEXT", StringEntry.class);
    }

    public static class ForeignKeys {
        public static ForeignKey<Profile> profile = new ForeignKey<>("ProfileId", "profile_id", new Profile());
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
