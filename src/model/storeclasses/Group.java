package model.storeclasses;

import view.panes.entry_panes.StringEntry;
import view.simple_panes.SampleClass;

import java.util.ArrayList;

public class Group extends StoreClass {

   private static int sampleId=1;

   private String groupName;
   private String colorHex;
   private int bankAccountId;
   private int sum;

   public Group() {
      tableName = "Groups";
      choiceBoxMethodName ="GroupName";
   }

   public static class Variables {
      public static FieldName id =FieldName.storeId();
      public static FieldName group_name = new FieldName("GroupName", "group_name","TEXT", StringEntry.class);
      public static FieldName color_hex= new FieldName("ColorHex", "color_hex","TEXT", StringEntry.class);
   }

   public static class ForeignKeys{
      public static ForeignKey<BankAccount> bankAccount = new ForeignKey<BankAccount>("BankAccountId","bankAccount_id", new BankAccount());
   }

   public void addSum(int s){
      sum +=s;
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

   public int getBankAccountId() {
      return bankAccountId;
   }

   public void setBankAccountId(int bankAccountId) {
      this.bankAccountId = bankAccountId;
   }

   public static Group sampleGroup(){
      Group sample = new Group();
      sample.setId(sampleId);
      sample.setGroupName("Test");
      sample.setColorHex("#"+ SampleClass.random(0,9)+SampleClass.random(0,9)+SampleClass.random(0,9));
      sampleId++;
      return sample;
   }
}
