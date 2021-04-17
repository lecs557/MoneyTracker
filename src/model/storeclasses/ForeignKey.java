package model.storeclasses;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ForeignKey<T extends StoreClass> {

    private final T dummyClazz;
    private final String programName;
    private final String sqlName;
    private ArrayList<T> foreignObjects = new ArrayList<>();

    public ForeignKey(String programName, String sqlName, T dummyClazz) {
        this.programName = programName;
        this.sqlName = sqlName;
        this.dummyClazz = dummyClazz;
    }

    public T getDummyClazz() {
        return dummyClazz;
    }

    public String getSqlName() {
        return sqlName;
    }

    public String getProgramName() {
        return programName;
    }

    public ArrayList<T> getForeignObjects() {
        return foreignObjects;
    }

    public void setForeignObjects(ArrayList<T> foreignObjects) {
        this.foreignObjects = foreignObjects;
    }

    public void set(T foreignObject){
        this.foreignObjects.clear();
        this.foreignObjects.add(foreignObject);
    }
}
