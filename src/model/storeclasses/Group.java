package model.storeclasses;

import view.panes.entry_panes.ColorEntry;
import view.panes.entry_panes.StringEntry;
import view.simple_panes.SampleClass;

import java.util.*;

public class Group extends StoreClass {
   private static int sampleId=1;
   private String groupName;
   private String color;
   private int bankAccountId;
   private final SortedMap<Integer,Integer> yearSumMap=new TreeMap<>();

   public Group() {
      tableName = "Groups";
      choiceBoxMethodName ="GroupName";
   }

   public static class Variables {
      public static FieldName id =FieldName.storeId();
      public static FieldName group_name = new FieldName("GroupName", "group_name","TEXT", StringEntry.class);
      public static FieldName color= new FieldName("Color", "color","TEXT", ColorEntry.class);
   }

   public static class ForeignKeys{
      public static ForeignKey<BankAccount> bankAccount = new ForeignKey<BankAccount>("BankAccountId","bankAccount_id", new BankAccount());
   }

   public void addSum(int year, int sum){
      yearSumMap.merge(year,sum,Integer::sum);
   }

   public void reset(int year){
      yearSumMap.put(year,0);
   }

   public Map<Integer, Integer> getYearSumMap() {
      return yearSumMap;
   }

   public int computeGroupTotal(){
      int sum=0;
      for(int inte:yearSumMap.values()){
         sum+=inte;
      }
      return sum;
   }

   public String getGroupName() {
      return groupName;
   }

   public void setGroupName(String groupName) {
      this.groupName = groupName;
   }

   public String getColor() {
      return color;
   }

   public void setColor(String color) {
      this.color = color;
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
      sample.setGroupName("Group"+sampleId);
      sample.setColor("#"+ SampleClass.random(8,9)+SampleClass.random(7,9)+SampleClass.random(6,9));
      sampleId++;
      return sample;
   }
}
