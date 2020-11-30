package model.storeclasses;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class LookUpTable<T extends StoreClass> {

    private String tableName;
    private ArrayList<String> primaryKeys = new ArrayList<>();
    protected ArrayList<Class<T>> storeClasses = new ArrayList<>();

    public LookUpTable() {

    }

    public <T extends StoreClass> void makePrim(ArrayList<Class<T>> storeClasses){
        for (Class<T> cla : storeClasses) {
            T dummyClass = null;
            try {
                dummyClass = cla.getDeclaredConstructor().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            for (FieldName fieldName: dummyClass.getFieldNames()){
                if(fieldName.sqlType.contains("PRIMARY KEY")){
                    primaryKeys.add(dummyClass.getTableName()+"_"+fieldName.sqlName);
                }
            }
        }
    }

    public ArrayList<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public String getTableName() {
        return tableName;
    }
}

