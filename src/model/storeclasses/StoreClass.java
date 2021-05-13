package model.storeclasses;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * abstrakte Klasse. Sie bestimmt, wie eine Tabelle in der
 * Datenenbank aussieht
 */
public abstract class StoreClass {

    private int id = 0;
    protected String tableName;
    protected String choiceBoxMethodName;
    protected ArrayList<FieldName> fieldNames = new ArrayList<>();
    protected ArrayList<ForeignKey<? extends StoreClass>> foreignKeys = new ArrayList<>();

    public StoreClass() {
        choiceBoxMethodName="Name";
        fieldNames.add(FieldName.storeId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChoiceBoxMethodName() {
        return choiceBoxMethodName;
    }

    public String getTableName() {
        return tableName;
    }

}
