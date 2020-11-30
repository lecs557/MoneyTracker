package model.storeclasses;

import java.util.ArrayList;

public class ForeignKey<T> {

    private String sqlName;
    private  T foreign;

    public ForeignKey(String sqlName, T foreign) {
        this.sqlName = sqlName;
        this.foreign=foreign;
    }

    public String getSqlName() {
        return sqlName;
    }

    public T getForeign() {
        return foreign;
    }
}
