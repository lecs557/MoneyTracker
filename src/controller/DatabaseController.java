package controller;

import model.storeclasses.FieldName;
import model.storeclasses.ForeignKey;
import model.storeclasses.StoreClass;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DatabaseController {
    static String DB_NAME = "Aurum_Observa";
    static String DB_PATH = "JDBC:sqlite:"+DB_NAME+".db";

    private static Connection conn = null;
    private static Statement stmt = null;

    public static <T extends StoreClass> ArrayList<T> computeStoreClasses(T storeClass, String orderBy) {
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
            fromBuilder.append(storeClass.getTableName());

            Iterator<FieldName> fieldNameIterator = storeClass.getFieldNames().iterator();
            while(fieldNameIterator.hasNext()){
                FieldName fieldName = fieldNameIterator.next();
                selectBuilder.append(fieldName.getSqlName());
                if(fieldNameIterator.hasNext()){
                    selectBuilder.append(", ");
                }
            }

            Iterator<ForeignKey<? extends StoreClass>> foreignKeyIterator = storeClass.getForeignKeys().iterator();
            if(!foreignKeyIterator.hasNext()) {
                whereBuilder = new StringBuilder();

            } else {
                if (!selectBuilder.toString().endsWith("SELECT ")) {
                    selectBuilder.append(", ");
                }
                while (foreignKeyIterator.hasNext()) {
                    ForeignKey<? extends StoreClass> key = foreignKeyIterator.next();
                    Iterator<? extends StoreClass> objectIterator = key.getForeignObjects().iterator();

                    if (objectIterator.hasNext()){
                        if(whereBuilder.toString().contains(")")){
                            whereBuilder.append(" AND ");
                        }
                        whereBuilder.append("(");
                    }
                    selectBuilder.append(key.getSqlName());

                    while (objectIterator.hasNext()) {
                        StoreClass object = objectIterator.next();
                        whereBuilder.append(key.getSqlName()).append("=").append(object.getId());
                        if (objectIterator.hasNext()) {
                            whereBuilder.append(" OR ");
                        } else{
                            whereBuilder.append(")");
                        }
                    }
                    if (foreignKeyIterator.hasNext()) {
                        selectBuilder.append(", ");
                    }
                }
            }
            String sql = selectBuilder+" "+fromBuilder+" "+whereBuilder;
            if (!orderBy.isEmpty()){
                sql+=" OREDER BY "+orderBy;
            }
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                T tempStoreClass = (T) storeClass.getClass().getDeclaredConstructor().newInstance();
                for(FieldName fieldName: storeClass.getFieldNames()){
                    Method method = storeClass.getClass().getMethod("set"+fieldName.getProgramName(),Class.forName("java.lang.String"));
                    method.invoke(tempStoreClass,rs.getString(fieldName.getSqlName()));
                }
                for(ForeignKey<? extends StoreClass> key: storeClass.getForeignKeys()){
                    Method method = storeClass.getClass().getMethod("set"+key.getProgramName(),Class.forName("java.lang.String"));
                    method.invoke(tempStoreClass,rs.getString(key.getSqlName()));
                }
                storeClasses.add(tempStoreClass);
            }
            close();
        } catch(SQLException se){
            close();
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
        return  storeClasses;
    }

    public static <T extends StoreClass> void storeObject(T storeClass, boolean checkIfExists){
        try {
            open();
            ResultSet table = conn.getMetaData().getTables(null,null,storeClass.getTableName(),null);
            if (!table.next()){
                System.out.println("Tabelle "+storeClass.getTableName()+" existiert nicht");
                createTable(storeClass.getClass());
            }

            StringBuilder insertBuilder = new StringBuilder("INSERT INTO ");
            StringBuilder valuesBuilder = new StringBuilder("VALUES( ");
            StringBuilder existsBuilder = new StringBuilder("WHERE ");
            insertBuilder.append(storeClass.getTableName());
            insertBuilder.append("(");

            Iterator<FieldName> fieldNameIterator = storeClass.getFieldNames().iterator();
            while (fieldNameIterator.hasNext()) {
                FieldName name = fieldNameIterator.next();
                if (!name.getProgramName().equals("Id")) {
                    insertBuilder.append(name.getSqlName());
                    existsBuilder.append(name.getSqlName()).append("=");
                    Method method = storeClass.getClass().getMethod("get" + name.getProgramName());
                    String content = (String) method.invoke(storeClass);
                    valuesBuilder.append("'").append(content).append("'");
                    existsBuilder.append("'").append(content).append("'");
                    if (fieldNameIterator.hasNext()) {
                        insertBuilder.append(", ");
                        valuesBuilder.append(", ");
                        existsBuilder.append(" AND ");
                    }
                }
            }

            Iterator<ForeignKey<? extends StoreClass>> foreignKeyIterator = storeClass.getForeignKeys().iterator();
            if(!insertBuilder.toString().endsWith("(") && foreignKeyIterator.hasNext() ){
                insertBuilder.append(", ");
                valuesBuilder.append(", ");
            }else {
                insertBuilder.append(")");
                valuesBuilder.append(")");
            }
            while (foreignKeyIterator.hasNext()) {
                ForeignKey<? extends StoreClass> key = foreignKeyIterator.next();
                insertBuilder.append(key.getSqlName());
                if (key.getForeignObjects().get(0) != null) {
                    valuesBuilder.append("'").append(key.getForeignObjects().get(0).getId()).append("'");
                } else {
                    valuesBuilder.append("'").append("NULL").append("'");
                }
                if (foreignKeyIterator.hasNext()) {
                    insertBuilder.append(", ");
                    valuesBuilder.append(", ");
                }else {
                    insertBuilder.append(")");
                    valuesBuilder.append(")");
                }
            }

            String id="b";
            if (checkIfExists){
                System.out.println("CHECK IF EXISTS");
                String sql = "SELECT id FROM "+ storeClass.getTableName() +" "+existsBuilder;
                System.out.println(sql);
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    id = rs.getString("id");
                    storeClass.setId(id);
                    System.out.println("EXISTS");
                }
            }
            if (id.equals("b")) {
                System.out.println("DOES NOT EXIST OR NOT CHECKED");
                String sql = insertBuilder + " " + valuesBuilder;
                System.out.println(sql);
                stmt.executeUpdate(sql);
                String sql1 = "SELECT id FROM " + storeClass.getTableName() + " ORDER BY id DESC LIMIT 1";
                ResultSet rs = stmt.executeQuery(sql1);
                while (rs.next()) {
                    id = rs.getString("id");
                    storeClass.setId(id);
                }
            }


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

    public static <T extends StoreClass> void updateObject(T storeClass){
        try {
            open();
            StringBuilder updateBuilder = new StringBuilder("UPDATE ");
            StringBuilder setBuilder = new StringBuilder("SET ");
            StringBuilder whereBuilder = new StringBuilder("WHERE ");
            updateBuilder.append(storeClass.getTableName());
            updateBuilder.append(" ");

            Iterator<FieldName> fieldNameIterator = storeClass.getFieldNames().iterator();
            while (fieldNameIterator.hasNext()) {
                FieldName name = fieldNameIterator.next();
                if (name.getProgramName().equals("Id")) {
                    whereBuilder.append("id=").append(storeClass.getId());
                } else {
                    setBuilder.append(name.getSqlName()).append("=");
                    Method method = storeClass.getClass().getMethod("get" + name.getProgramName());
                    setBuilder.append("'").append(method.invoke(storeClass)).append("'");
                    if (fieldNameIterator.hasNext()) {
                        setBuilder.append(", ");
                    }
                }
            }

            Iterator<ForeignKey<? extends StoreClass>> foreignKeyIterator = storeClass.getForeignKeys().iterator();
            if(foreignKeyIterator.hasNext() && !setBuilder.toString().endsWith("SET ")){
                setBuilder.append(", ");
            }
            while (foreignKeyIterator.hasNext()) {
                ForeignKey<? extends StoreClass> key = foreignKeyIterator.next();
                setBuilder.append(key.getSqlName()).append("=");
                setBuilder.append("'").append(key.getForeignObjects().get(0).getId()).append("'");
                if (foreignKeyIterator.hasNext()) {
                    setBuilder.append(", ");
                }
            }
            System.out.println(updateBuilder+" "+setBuilder+" "+whereBuilder);
            stmt.executeUpdate(updateBuilder+" "+setBuilder+" "+whereBuilder);
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
            StringBuilder foreignKeyBuilder = new StringBuilder();
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

            Iterator<ForeignKey<? extends StoreClass>> foreignKeyIterator = dummyClass.getForeignKeys().iterator();
            if(foreignKeyIterator.hasNext() && !createTableBuilder.toString().endsWith("(")){
                createTableBuilder.append(", ");
            }
            while (foreignKeyIterator.hasNext()) {
                ForeignKey<? extends StoreClass> key = foreignKeyIterator.next();
                createTableBuilder.append(key.getSqlName()).append(" INTEGER");
                foreignKeyBuilder.append("FOREIGN KEY(").append(key.getSqlName()).append(") ");
                foreignKeyBuilder.append("REFERENCES ").append(key.getForeignObjects().get(0).getTableName());
                if (foreignKeyIterator.hasNext()) {
                    createTableBuilder.append(", ");
                    foreignKeyBuilder.append(", ");
                } else {
                    createTableBuilder.append(",");
                    foreignKeyBuilder.append(")");
                }
            }
            System.out.println(createTableBuilder+" "+foreignKeyBuilder);
            stmt.executeUpdate(createTableBuilder+" "+foreignKeyBuilder);
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

    public static void setDbName(String dbName) {
        DB_NAME = dbName;
    }

    private static void open() throws ClassNotFoundException, SQLException {
        if( (conn == null || conn.isClosed()) ){
            conn = DriverManager.getConnection(DB_PATH);
        }
        if( (stmt == null || stmt.isClosed()) ){
            stmt = conn.createStatement();
            System.out.println("SQL START");
        }
    }

    private static void close(){
        try{
            if(stmt!=null) {
                stmt.close();
                System.out.println("SQL END");
            }
        }catch(SQLException se2){
            se2.printStackTrace();
        }
        try{
            if(conn!=null) {
                conn.close();
            }
        }catch(SQLException se){
            se.printStackTrace();
        }

    }
}

