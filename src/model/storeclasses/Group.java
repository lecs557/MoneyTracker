package model.storeclasses;

import view.panes.entry_panes.DateEntry;
import view.panes.entry_panes.StringEntry;

import java.util.ArrayList;

public class Group extends StoreClass {

   private String groupName;
   private String colorHex;

   public Group() {
      tableName = "Groups";
      fieldNames.add(new FieldName("GroupName", "group_name","TEXT", StringEntry.class));
      fieldNames.add(new FieldName("Color", "color","TEXT", StringEntry.class));
      foreignKeys.add(new ForeignKey<>("bank_account_id", new BankAccount()));
      choiceBoxMethodName ="GroupName";
   }

   public void setForeignKeyBankAccount(ArrayList<BankAccount> bankAccounts){
      ((ForeignKey<BankAccount>)getForeignKeys().get(0)).setForeigns(bankAccounts);
   }

   public String getGroupName() {
      return groupName;
   }

   public void setGroupName(String groupName) {
      this.groupName = groupName;
   }

   public String getColorHex() {
      return colorHex;
   }

   public void setColorHex(String colorHex) {
      this.colorHex = colorHex;
   }
}
