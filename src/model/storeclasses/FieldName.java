package model.storeclasses;

import view.panes.EntryPane;
import view.panes.StringEntry;

public class FieldName {

    private final String programName;
    private final String sqlName;
    private final String sqlType;
    private final Class<? extends EntryPane> entryClass;

    public FieldName(String programName, String sqlName, String sqlType, Class<? extends EntryPane> entryClass) {
        this.programName = programName;
        this.sqlName = sqlName;
        this.sqlType =sqlType;
        this.entryClass = entryClass;
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

    public Class<? extends EntryPane> getEntryClass() {
        return entryClass;
    }

    public static FieldName storeId(){
        return new FieldName("Id", "id","INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT", StringEntry.class);
    }
}
