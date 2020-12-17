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

    public static <T extends StoreClass> ArrayList<T> loadStoreClass(Class<T> storeClass) {
        ArrayList<T> storeClasses = new ArrayList<>();
        try{
            T dummyClass = storeClass.getDeclaredConstructor().newInstance();
            open();
            StringBuilder sql = new StringBuilder("SELECT ");
            Iterator<FieldName> fieldNameIteraor = dummyClass.getFieldNames().iterator();
            while(fieldNameIteraor.hasNext()){
                sql.append(fieldNameIteraor.next().getSqlName());
                if(fieldNameIteraor.hasNext()){
                    sql.append(", ");
                } else {
                    sql.append(" ");
                }
            }
            sql.append("FROM ").append(dummyClass.getTableName());
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql.toString());
            while(rs.next()){
                T store = storeClass.getDeclaredConstructor().newInstance();
                for(FieldName name: storeClass.getDeclaredConstructor().newInstance().getFieldNames()){
                    Method method = storeClass.getMethod("set"+name.getProgramName(),Class.forName("java.lang.String"));
                    method.invoke(store,rs.getString(name.getSqlName()));
                }
                storeClasses.add(store);
            }
        } catch(SQLException se){
            se.printStackTrace();
            System.out.println("CREATE TABLE");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close();
        }
        return  storeClasses;
    }

    public static <T extends StoreClass> ArrayList<T> loadStoreClassFrom(T storeClass) {
        ArrayList<T> storeClasses = new ArrayList<>();
        try{
            open();
            StringBuilder sql = new StringBuilder("SELECT ");
            Iterator<FieldName> fieldNameIterator = storeClass.getFieldNames().iterator();
            while(fieldNameIterator.hasNext()){
                sql.append(fieldNameIterator.next().getSqlName());
                if(fieldNameIterator.hasNext()){
                    sql.append(", ");
                } else {
                    sql.append(" ");
                }
            }
            sql.append("FROM ").append(storeClass.getTableName());
            sql.append(" WHERE ");
            Iterator<ForeignKey<? extends StoreClass>> foreignIterator = storeClass.getForeignKeys().iterator();
            while(foreignIterator.hasNext()){
                ForeignKey<? extends StoreClass> key = foreignIterator.next();
                sql.append(key.getSqlName()).append("=").append(key.getForeign().getId());
                if(foreignIterator.hasNext()){
                    sql.append(", ");
                } else {
                    sql.append(" ");
                }
            }
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql.toString());
            while(rs.next()){
                for(FieldName name: storeClass.getFieldNames()){
                    Method method = storeClass.getClass().getMethod("set"+name.getProgramName(),Class.forName("java.lang.String"));
                    method.invoke(storeClass,rs.getString(name.getSqlName()));
                }
                storeClasses.add(storeClass);
            }
            close();
        } catch(SQLException se){
            se.printStackTrace();
            close();
            createTable(storeClass.getClass());
            System.out.println("CREATE TABLE");
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
        return  storeClasses;
    }

    public static <T extends StoreClass> ArrayList<T> loadtESTStoreClassFrom(T storeClass) {
        ArrayList<T> storeClasses = new ArrayList<>();
        try{
            open();
            StringBuilder selectBuilder = new StringBuilder("SELECT ");
            StringBuilder fromBuilder = new StringBuilder("FROM ");
            StringBuilder whereBuilder = new StringBuilder("WHERE ");
            Iterator<FieldName> fieldNameIterator = storeClass.getFieldNames().iterator();
            Iterator<ForeignKey<? extends StoreClass>> foreignObjectIterator = storeClass.getForeignObjects().iterator();
            fromBuilder.append(storeClass.getTableName());

            while(fieldNameIterator.hasNext()){
                FieldName fieldName = fieldNameIterator.next();
                selectBuilder.append(storeClass.getTableName());
                selectBuilder.append(".").append(fieldName.getSqlName());
                selectBuilder.append(" AS ").append(storeClass.getTableName());
                selectBuilder.append(fieldName.getSqlName());

                if(fieldNameIterator.hasNext()){
                    selectBuilder.append(", ");
                }
            }

            if(foreignObjectIterator.hasNext()){
                selectBuilder.append(", ");
                fromBuilder.append(", ");

                while(foreignObjectIterator.hasNext()) {

                    ForeignKey<? extends StoreClass> foreignObject = foreignObjectIterator.next();

                    fromBuilder.append(foreignObject.getForeign().getTableName());
                    whereBuilder.append(foreignObject.getSqlName()).append("=");
                    whereBuilder.append(foreignObject.getForeign().getTableName());
                    whereBuilder.append(".id");

                    Iterator<FieldName> foreignObjectFieldNameIterator = foreignObject.getForeign().getFieldNames().iterator();
                    while (foreignObjectFieldNameIterator.hasNext()) {
                        FieldName foreignFieldName = foreignObjectFieldNameIterator.next();
                        selectBuilder.append(foreignObject.getForeign().getTableName());
                        selectBuilder.append(".").append(foreignFieldName.getSqlName());
                        selectBuilder.append(" AS ").append(foreignObject.getForeign().getTableName());
                        selectBuilder.append(foreignFieldName.getSqlName());
                        if (foreignObjectFieldNameIterator.hasNext()) {
                            selectBuilder.append(", ");
                        }
                    }
                    if (foreignObjectIterator.hasNext()) {
                        whereBuilder.append(" AND ");
                        fromBuilder.append(", ");
                    }
                }
            }
            Iterator<ForeignKey<? extends StoreClass>> foreignIterator = storeClass.getForeignKeys().iterator();
            if(!storeClass.getForeignObjects().isEmpty()){
                whereBuilder.append(" AND ");
            }
            while(foreignIterator.hasNext()){
                ForeignKey<? extends StoreClass> key = foreignIterator.next();
                whereBuilder.append(key.getSqlName()).append("=").append(key.getForeign().getId());
                if(foreignIterator.hasNext()){
                    whereBuilder.append(", ");
                }
            }

            System.out.println(selectBuilder+" "+fromBuilder+" "+whereBuilder);
            ResultSet rs = stmt.executeQuery(selectBuilder+" "+fromBuilder+" "+whereBuilder);

            while(rs.next()){
                T tempStoreClass = (T) storeClass.getClass().getDeclaredConstructor().newInstance();
                for(FieldName fieldName: storeClass.getFieldNames()){
                    Method method = storeClass.getClass().getMethod("set"+fieldName.getProgramName(),Class.forName("java.lang.String"));
                    method.invoke(tempStoreClass,rs.getString(storeClass.getTableName()+fieldName.getSqlName()));
                }

                for(ForeignKey<? extends StoreClass> foreignObject: tempStoreClass.getForeignObjects()){
                    for(FieldName foreignObjectFieldName: foreignObject.getForeign().getFieldNames()){
                        Method method = foreignObject.getForeign().getClass().getMethod("set"+foreignObjectFieldName.getProgramName(),Class.forName("java.lang.String"));
                        method.invoke(foreignObject.getForeign(),rs.getString(foreignObject.getForeign().getTableName()+foreignObjectFieldName.getSqlName()));
                    }
                    Class<? extends StoreClass> tempclazz = foreignObject.getForeign().getClass();
                    Method method = storeClass.getClass().getMethod("set"+tempclazz.getSimpleName(),tempclazz);
                    method.invoke(tempStoreClass,foreignObject.getForeign());
                }
                storeClasses.add(tempStoreClass);
            }
            close();
        } catch(SQLException se){
            se.printStackTrace();
            close();
            System.out.println("CREATE TABLE");
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
        return  storeClasses;
    }

    public static <T extends StoreClass> void storeObject(T storeClass){
        try {
            open();
            StringBuilder insert = new StringBuilder("INSERT INTO ");
            StringBuilder values = new StringBuilder(" VALUES(");
            insert.append(storeClass.getTableName());
            insert.append(" (");
            Iterator<FieldName> fieldNameIterator = storeClass.getFieldNames().iterator();
            while (fieldNameIterator.hasNext()) {
                FieldName name = fieldNameIterator.next();
                if (!name.getProgramName().equals("Id")) {
                    insert.append(name.getSqlName());
                    Method method = storeClass.getClass().getMethod("get" + name.getProgramName());
                    values.append("'").append(method.invoke(storeClass)).append("'");
                    if (fieldNameIterator.hasNext()) {
                        insert.append(", ");
                        values.append(", ");
                    }
                }
            }
            Iterator<ForeignKey<? extends StoreClass>> foreignIterator = storeClass.getForeignKeys().iterator();
            if(foreignIterator.hasNext()){
                insert.append(", ");
                values.append(", ");
            }else {
                insert.append(")");
                values.append(")");
            }
            while (foreignIterator.hasNext()) {
                ForeignKey<? extends StoreClass> name = foreignIterator.next();
                insert.append(name.getSqlName());
                values.append("'").append(name.getForeign().getId()).append("'");
                if (foreignIterator.hasNext()) {
                    insert.append(", ");
                    values.append(", ");
                }else {
                    insert.append(")");
                    values.append(")");
                }
            }
            System.out.println(insert.toString()+values.toString());
            stmt.executeUpdate(insert.toString()+values.toString());
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
            StringBuilder sql = new StringBuilder("CREATE TABLE ");
            sql.append(dummyClass.getTableName());
            sql.append("(");
            Iterator<FieldName> iterator = dummyClass.getFieldNames().iterator();
            while(iterator.hasNext()){
                FieldName name = iterator.next();
                sql.append(name.getSqlName()).append(" ");
                sql.append(name.getSqlType());
                if(iterator.hasNext()){
                    sql.append(", ");
                }
            }
            Iterator<ForeignKey<? extends StoreClass>> foreignkeyIterator = dummyClass.getForeignKeys().iterator();
            if(foreignkeyIterator.hasNext()){
                sql.append(", ");
            }
            while (foreignkeyIterator.hasNext()) {
                ForeignKey<? extends StoreClass> key = foreignkeyIterator.next();
                sql.append(key.getSqlName()).append(" INTEGER");
                sql.append(", ");
                sql.append("FOREIGN KEY(").append(key.getSqlName()).append(") ");
                sql.append("REFERENCES ").append(key.getForeign().getTableName());
                if (foreignkeyIterator.hasNext()) {
                    sql.append(", ");
                } else {
                    sql.append(")");
                }
            }
            System.out.println(sql);
            stmt.executeUpdate(sql.toString());
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

