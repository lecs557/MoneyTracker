package model.storeclasses;

 public class FieldName {

    private final String programRefName;
    private final String sqlName;
    private final String sqlType;

    public FieldName(String programRefName, String sqlName, String sqlType) {
        this.programRefName = programRefName;
        this.sqlName = sqlName;
        this.sqlType =sqlType;
    }

     public String getProgramRefName() {
         return programRefName;
     }

     public String getSqlName() {
         return sqlName;
     }

     public String getSqlType() {
         return sqlType;
     }

     public static FieldName storeId(){
        return new FieldName("Id", "id","INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT");
    }
}
