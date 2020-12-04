package model.storeclasses;

import java.util.ArrayList;

public class ForeignKey<T extends StoreClass> {

    private String sqlName;
    private T foreign;
    private ArrayList<Integer> ids = new ArrayList<>();

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

    public void setForeign(T foreign) {
        this.foreign = foreign;
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }
}
