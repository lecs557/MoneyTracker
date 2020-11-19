package model;

 public class FieldName {

    public final String programm_name;
    public final String sql_name;
    public final String sql_type;

    public FieldName(String programm_name, String sql_name, String sql_type) {
        this.programm_name = programm_name;
        this.sql_name = sql_name;
        this.sql_type=sql_type;
    }
}
