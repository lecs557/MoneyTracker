package controller;

import model.storeclasses.FieldName;
import model.storeclasses.ForeignKey;
import model.storeclasses.LookUpTable;
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
            Iterator<FieldName> iterator = dummyClass.getFieldNames().iterator();
            while(iterator.hasNext()){
                sql.append(iterator.next().sqlName);
                if(iterator.hasNext()){
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
                    Method method = storeClass.getMethod("set"+name.programRefName,Class.forName("java.lang.String"));
                    method.invoke(store,rs.getString(name.sqlName));
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

    public static <T extends StoreClass> String storeObject(T storeClass){
        String id="-1" ;
        try {
            open();
            StringBuilder sql = new StringBuilder("INSERT INTO ");
            sql.append(storeClass.getTableName());
            sql.append(" (");
            Iterator<FieldName> iterator = storeClass.getFieldNames().iterator();
            while (iterator.hasNext()) {
                FieldName name = iterator.next();
                if (!name.getProgramRefName().equals("Id")) {
                    sql.append(name.getSqlName());
                    if (iterator.hasNext()) {
                        sql.append(", ");
                    } else {
                        sql.append(")");
                    }
                }
            }
            sql.append(" VALUES(");
            iterator = storeClass.getFieldNames().iterator();
            while (iterator.hasNext()) {
                FieldName name = iterator.next();
                if (!name.getProgramRefName().equals("Id")) {
                    Method method = storeClass.getClass().getMethod("get" + name.getProgramRefName());
                    sql.append("'").append(method.invoke(storeClass)).append("'");
                    if (iterator.hasNext()) {
                        sql.append(", ");
                    } else {
                        sql.append(")");
                    }
                }
            }
            System.out.println(sql);
            stmt.executeUpdate(sql.toString());
            ResultSet rs = stmt.executeQuery("SELECT id FROM "+ storeClass.getTableName() +" ORDER BY id DESC LIMIT 1");
            id = rs.getString("id");
        } catch(SQLException se){
            se.printStackTrace();
            System.out.println("CREATE TABLE");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close();
        }
        return id;
    }

    public static <T extends StoreClass> void  createTable(Class<T> storeClass) {
        try{
            T dummyClass = storeClass.getDeclaredConstructor().newInstance();
            open();
            StringBuilder sql = new StringBuilder("CREATE TABLE ");
            sql.append(dummyClass.getTableName());
            sql.append("(");
            Iterator<FieldName> iterator = dummyClass.getFieldNames().iterator();
            while(iterator.hasNext()){
                FieldName name = iterator.next();
                sql.append("'").append(name.getSqlType()).append("' ");
                sql.append(name.getSqlType());
                if(iterator.hasNext()){
                    sql.append(", ");
                }
            }
            sql.append(", ");
            Iterator<ForeignKey<? extends StoreClass>> foreignkeyIterator = dummyClass.getForeignKeys().iterator();
            while (foreignkeyIterator.hasNext()) {
                ForeignKey<? extends StoreClass> key = foreignkeyIterator.next();
                sql.append(key.getSqlName()).append(" INTEGER");
                if (foreignkeyIterator.hasNext()) {
                    sql.append(", ");
                }
            }
            sql.append(", ");
            foreignkeyIterator = dummyClass.getForeignKeys().iterator();
            while (foreignkeyIterator.hasNext()) {
                ForeignKey<? extends StoreClass> key = foreignkeyIterator.next();
                sql.append("FOREIGN KEY(").append(key).append(") ");
                sql.append("REFERENCES ").append(key.getForeign().getTableName();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
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
        } finally{
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

