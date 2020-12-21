package model.storeclasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ForeignKey<T extends StoreClass> {

    private String sqlName;
    private ArrayList<T> foreigns = new ArrayList<>();

    public ForeignKey(String sqlName, T foreign) {
        this.sqlName = sqlName;
        this.foreigns.add(foreign);
    }

    public String getSqlName() {
        return sqlName;
    }

    public ArrayList<T> getForeigns() {
        return foreigns;
    }

    public void setForeigns(ArrayList<T> foreigns) {
        this.foreigns = foreigns;
    }
}
