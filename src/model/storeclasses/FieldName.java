package model.storeclasses;

 public class FieldName {

    private final String programName;
    private final String sqlName;
    private final String sqlType;

    public FieldName(String programName, String sqlName, String sqlType) {
        this.programName = programName;
        this.sqlName = sqlName;
        this.sqlType =sqlType;
    }

     public String getProgramName() {
         return programName;
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
