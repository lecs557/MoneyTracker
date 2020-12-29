package controller;

import model.storeclasses.FieldName;
import model.storeclasses.ForeignKey;
import model.storeclasses.StoreClass;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DatabaseController {
    static final String DB_NAME = "Aurum_Observa";
    static final String DB_PATH = "JDBC:sqlite:"+DB_NAME+".db";

    private static Connection conn = null;
    private static Statement stmt = null;

    public static <T extends StoreClass> ArrayList<T> computeStoreClasses(T storeClass) {
        ArrayList<T> storeClasses = new ArrayList<>();
        try{
            open();
            ResultSet table = conn.getMetaData().getTables(null,null,storeClass.getTableName(),null);
            if (!table.next()){
                System.out.println("Tabelle "+storeClass.getTableName()+" existiert nicht");
                createTable(storeClass.getClass());
                return storeClasses;
            }

            StringBuilder selectBuilder = new StringBuilder("SELECT ");
            StringBuilder fromBuilder = new StringBuilder("FROM ");
            StringBuilder whereBuilder = new StringBuilder("WHERE ");
            Iterator<FieldName> fieldNameIterator = storeClass.getFieldNames().iterator();
            fromBuilder.append(storeClass.getTableName());

            while(fieldNameIterator.hasNext()){
                FieldName fieldName = fieldNameIterator.next();
                selectBuilder.append(fieldName.getSqlName());

                if(fieldNameIterator.hasNext()){
                    selectBuilder.append(", ");
                }
            }

            Iterator<ForeignKey<? extends StoreClass>> foreignIterator = storeClass.getForeignKeyIterator().iterator();
            if(!foreignIterator.hasNext()) {
                whereBuilder = new StringBuilder();
            } else {
                whereBuilder.append("(");
                while (foreignIterator.hasNext()) {
                    ForeignKey<? extends StoreClass> key = foreignIterator.next();
                    Iterator<? extends StoreClass> keyIterator = key.getForeigns().iterator();

                    while (keyIterator.hasNext()) {
                        StoreClass foreign = keyIterator.next();

                        whereBuilder.append(key.getSqlName()).append("=").append(foreign.getId());

                        if (keyIterator.hasNext()) {
                            whereBuilder.append(" OR ");
                        }
                    }
                    if (foreignIterator.hasNext()) {
                        whereBuilder.append(") AND (");
                    }
                }
                whereBuilder.append(")");
            }
            System.out.println(selectBuilder+" "+fromBuilder+" "+whereBuilder);
            ResultSet rs = stmt.executeQuery(selectBuilder+" "+fromBuilder+" "+whereBuilder);

            while(rs.next()){
                T tempStoreClass = (T) storeClass.getClass().getDeclaredConstructor().newInstance();
                for(FieldName fieldName: storeClass.getFieldNames()){
                    Method method = storeClass.getClass().getMethod("set"+fieldName.getProgramName(),Class.forName("java.lang.String"));
                    method.invoke(tempStoreClass,rs.getString(fieldName.getSqlName()));
                }
                storeClasses.add(tempStoreClass);
            }
            close();
        } catch(SQLException se){
            se.printStackTrace();
            close();
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
        return  storeClasses;
    }

    public static <T extends StoreClass> void storeObject(T storeClass){
        try {
            open();
            StringBuilder insertBuilder = new StringBuilder("INSERT INTO ");
            StringBuilder valuesBuilder = new StringBuilder("VALUES( ");
            insertBuilder.append(storeClass.getTableName());
            insertBuilder.append("(");

            Iterator<FieldName> fieldNameIterator = storeClass.getFieldNames().iterator();
            while (fieldNameIterator.hasNext()) {
                FieldName name = fieldNameIterator.next();
                if (!name.getProgramName().equals("Id")) {
                    insertBuilder.append(name.getSqlName());
                    Method method = storeClass.getClass().getMethod("get" + name.getProgramName());
                    valuesBuilder.append("'").append(method.invoke(storeClass)).append("'");
                    if (fieldNameIterator.hasNext()) {
                        insertBuilder.append(", ");
                        valuesBuilder.append(", ");
                    }
                }
            }

            Iterator<ForeignKey<? extends StoreClass>> foreignIterator = storeClass.getForeignKeyIterator().iterator();
            if(foreignIterator.hasNext()){
                insertBuilder.append(", ");
                valuesBuilder.append(", ");
            }else {
                insertBuilder.append(")");
                valuesBuilder.append(")");
            }
            while (foreignIterator.hasNext()) {
                ForeignKey<? extends StoreClass> name = foreignIterator.next();
                insertBuilder.append(name.getSqlName());
                valuesBuilder.append("'").append(name.getForeigns().get(0).getId()).append("'");
                if (foreignIterator.hasNext()) {
                    insertBuilder.append(", ");
                    valuesBuilder.append(", ");
                }else {
                    insertBuilder.append(")");
                    valuesBuilder.append(")");
                }
            }
            System.out.println(insertBuilder+" "+valuesBuilder);
            stmt.executeUpdate(insertBuilder+" "+valuesBuilder);
            close();
        } catch(SQLException se){
            se.printStackTrace();
            System.out.println("CREATE TABLE");
            close();
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
    }

    public static <T extends StoreClass> void createTable(Class<T> storeClass) {
        try{
            T dummyClass = storeClass.getDeclaredConstructor().newInstance();
            open();
            StringBuilder createTableBuilder = new StringBuilder("CREATE TABLE ");
            createTableBuilder.append(dummyClass.getTableName());
            createTableBuilder.append("(");

            Iterator<FieldName> iterator = dummyClass.getFieldNames().iterator();
            while(iterator.hasNext()){
                FieldName name = iterator.next();
                createTableBuilder.append(name.getSqlName()).append(" ");
                createTableBuilder.append(name.getSqlType());
                if(iterator.hasNext()){
                    createTableBuilder.append(", ");
                }
            }

            Iterator<ForeignKey<? extends StoreClass>> foreignKeyIterator = dummyClass.getForeignKeyIterator().iterator();
            if(foreignKeyIterator.hasNext()){
                createTableBuilder.append(", ");
            }
            while (foreignKeyIterator.hasNext()) {
                ForeignKey<? extends StoreClass> key = foreignKeyIterator.next();
                createTableBuilder.append(key.getSqlName()).append(" INTEGER");
                createTableBuilder.append(", ");
                createTableBuilder.append("FOREIGN KEY(").append(key.getSqlName()).append(") ");
                createTableBuilder.append("REFERENCES ").append(key.getForeigns().get(0).getTableName());
                if (foreignKeyIterator.hasNext()) {
                    createTableBuilder.append(", ");
                } else {
                    createTableBuilder.append(")");
                }
            }
            System.out.println(createTableBuilder);
            stmt.executeUpdate(createTableBuilder.toString());
            System.out.println("Table created successfully...");
        } catch(SQLException se){
            se.printStackTrace();
            System.out.println("CREATE DataBase");
            close();
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
    }

    public static void createDataBase() {
        try{
            open();
            String sql = "CREATE DATABASE "+DB_NAME;
            System.out.println(sql);
            System.out.println("Creating database...");
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        } catch(Exception se){
            se.printStackTrace();
            close();
        }
    }

    private static void open() throws ClassNotFoundException, SQLException {
        if( (conn == null || conn.isClosed()) ){
            System.out.println("Connecting to database...");
            //Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DB_PATH);
        }
        if( (stmt == null || stmt.isClosed()) ){
            stmt = conn.createStatement();
            System.out.println("new statement...");
        }
    }

    private static void close(){
        try{
            if(stmt!=null) {
                stmt.close();
            }
        }catch(SQLException se2){
            se2.printStackTrace();
        }
        try{
            if(conn!=null) {
                conn.close();
                System.out.println("Connection closed");
            }
        }catch(SQLException se){
            se.printStackTrace();
        }

    }
}

