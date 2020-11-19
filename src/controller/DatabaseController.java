package controller;

import model.FieldName;
import model.StoreClass;
import model.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseController {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";

    static final String USER = "username";
    static final String PASS = "pssword";

    private static Connection conn = null;
    private static Statement stmt = null;

    public static void loadTransactionsOfYear(int year) {
        try{
            Transaction temp = new Transaction();
            open();
            StringBuilder sql = new StringBuilder("SELECT ");
            for(FieldName name: temp.getFieldNames()){
                sql.append(name.sql_name);
                if(temp.getFieldNames().iterator().hasNext()){
                    sql.append(", ");
                } else {
                    sql.append(" ");
                }
            }
            sql.append("WHERE YEAR(date) =").append(year);
            stmt.executeUpdate(sql.toString());
        } catch(Exception se){
            se.printStackTrace();
        } finally{
            close();
        }
    }

    public static void createTable(StoreClass storeClass) {
        try{
            open();
            StringBuilder sql = new StringBuilder("CREATE TABLE ");
            sql.append(storeClass.getTableName());
            sql.append(" VALUES(");
            for(FieldName name: storeClass.getFieldNames()){
                sql.append(name.sql_name).append(" ");
                sql.append(name.sql_type);
                if(storeClass.getFieldNames().iterator().hasNext()){
                    sql.append(", ");
                } else {
                    sql.append(") ");
                }
            }
            System.out.println("Creating Table...");
            stmt.executeUpdate(sql.toString());
            System.out.println("Table created successfully...");
        } catch(Exception se){
            se.printStackTrace();
        } finally{
            close();
        }
    }

    public static void createDataBase() {
        try{
            open();
            String sql = "CREATE DATABASE Aurum_Observa";
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
        Class.forName(JDBC_DRIVER);
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();
    }

    private static void close(){
        try{
            if(stmt!=null) {
                stmt.close();
            }
        }catch(SQLException se2){ }
        try{
            if(conn!=null) {
                conn.close();
            }
        }catch(SQLException se){
            se.printStackTrace();
        }

    }
}

