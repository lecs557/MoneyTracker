package model.storeclasses;

import view.panes.entry_panes.StringEntry;

import java.util.ArrayList;

public class Group extends StoreClass {

   private String groupName;
   private String colorHex;
   private String bankAccountId;

   public Group() {
      tableName = "Groups";
      foreignName = new FieldName("GroupId","group_id","",null);
      fieldNames.add(new FieldName("GroupName", "group_name","TEXT", StringEntry.class));
      fieldNames.add(new FieldName("Color", "color","TEXT", StringEntry.class));
      foreignKeys.add(new ArrayList<BankAccount>());
      choiceBoxMethodName ="GroupName";
   }

   public void setForeignKeyBankAccount(ArrayList<BankAccount> bankAccounts){
      foreignKeys.get(0).clear();
      ((ArrayList<BankAccount>) foreignKeys.get(0)).addAll(bankAccounts);
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
