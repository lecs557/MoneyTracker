package controller;

import model.storeclasses.FieldName;
import model.storeclasses.ForeignKey;
import model.storeclasses.StoreClass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 */
public class DatabaseController {

    private static DatabaseController singleton;

    private DatabaseController(String dbName){
        this.dbName =dbName;
    }

    public static void initialize(String dbName){
        if (singleton == null) {
            singleton = new DatabaseController(dbName);
            singleton.dbPath = "JDBC:sqlite:"+ dbName +".db";
        }
    }

    public static DatabaseController getInstance(){
        if (singleton == null) {
            initialize("Datenbank");
        }
        return singleton;
    }

    private String dbName = "";
    private String dbPath = "";

    private Connection conn = null;
    private Statement stmt = null;

    public <T extends StoreClass> ArrayList<T> computeStoreClasses(T storeClass, String orderBy) {
        ArrayList<T> resultStoreClasses = new ArrayList<>();
        try{
            open();
            ResultSet table = conn.getMetaData().getTables(null,null,storeClass.getTableName(),null);
            if (!table.next()){
                System.out.println("Tabelle "+storeClass.getTableName()+" existiert nicht und wird erstellt");
                createTable(storeClass.getClass());
                return resultStoreClasses;
            }

            StringBuilder selectBuilder = new StringBuilder("SELECT ");
            StringBuilder fromBuilder = new StringBuilder("FROM ");
            StringBuilder whereBuilder = new StringBuilder("WHERE ");
            fromBuilder.append(storeClass.getTableName());

            Iterator<Field> fieldNameFieldIterator = Arrays.asList(storeClass.getClass().getClasses()[1].getFields()).iterator();
            while(fieldNameFieldIterator.hasNext()){
                FieldName fieldName =(FieldName) fieldNameFieldIterator.next().get(storeClass);
                selectBuilder.append(fieldName.getSqlName());
                if(fieldNameFieldIterator.hasNext()){
                    selectBuilder.append(", ");
                }
            }

            Iterator<Field> foreignKeyFieldIterator = Arrays.asList(storeClass.getClass().getClasses()[0].getFields()).iterator();
            if(!foreignKeyFieldIterator.hasNext()) {
                whereBuilder = new StringBuilder();

            } else {
                if (!selectBuilder.toString().endsWith("SELECT ")) {
                    selectBuilder.append(", ");
                }
                while (foreignKeyFieldIterator.hasNext()) {
                    ForeignKey<? extends StoreClass> key = (ForeignKey<? extends StoreClass>) foreignKeyFieldIterator.next().get(storeClass);
                    Iterator<? extends StoreClass> foreignObjectIterator = key.getForeignObjects().iterator();

                    if (foreignObjectIterator.hasNext()){
                        if(whereBuilder.toString().contains(")")){
                            whereBuilder.append(" OR ");
                        }
                        whereBuilder.append("(");
                    }
                    selectBuilder.append(key.getSqlName());

                    while (foreignObjectIterator.hasNext()) {
                        StoreClass object = foreignObjectIterator.next();
                        whereBuilder.append(key.getSqlName()).append("=").append(object.getId());
                        if (foreignObjectIterator.hasNext()) {
                            whereBuilder.append(" OR ");
                        } else{
                            whereBuilder.append(")");
                        }
                    }
                    if (foreignKeyFieldIterator.hasNext()) {
                        selectBuilder.append(", ");
                    }
                }
                if(whereBuilder.toString().endsWith("WHERE ")){
                    whereBuilder = new StringBuilder();
                }
            }
            String sql = selectBuilder+" "+fromBuilder+" "+whereBuilder;
            if (!orderBy.isEmpty()){
                sql+=" ORDER BY "+orderBy;
            }
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                T tempStoreClass = (T) storeClass.getClass().getDeclaredConstructor().newInstance();
                for(Field field: storeClass.getClass().getClasses()[1].getFields()){
                    FieldName fieldName = (FieldName) field.get(storeClass);
                    if (fieldName.getProgramName().equals("Id")) {
                        Method method = storeClass.getClass().getMethod("set"+fieldName.getProgramName(),int.class);
                        method.invoke(tempStoreClass,rs.getInt(fieldName.getSqlName()));
                    } else {
                        Method method = storeClass.getClass().getMethod("set" + fieldName.getProgramName(), String.class);
                        method.invoke(tempStoreClass, rs.getString(fieldName.getSqlName()));
                    }
                }
                for(Field field: storeClass.getClass().getClasses()[0].getFields()){
                    ForeignKey<? extends StoreClass> key = (ForeignKey<? extends StoreClass>) field.get(storeClass);
                    Method method = storeClass.getClass().getMethod("set"+key.getProgramName(),int.class);
                    method.invoke(tempStoreClass,rs.getInt(key.getSqlName()));
                }
                resultStoreClasses.add(tempStoreClass);
            }
            close();
        } catch(SQLException se){
            close();
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
        return resultStoreClasses;
    }

    public <T extends StoreClass> boolean storeObject(T storeClass, boolean checkIfExists){
        if (storeClass.getId()>0) {
            System.out.println("ID existiert; wurde nicht in die DB aufgenommen");
            return false;
        }
        boolean added =false;
        try {
            open();
            ResultSet table = conn.getMetaData().getTables(null,null,storeClass.getTableName(),null);
            if (!table.next()){
                System.out.println("Tabelle "+storeClass.getTableName()+" existiert nicht");
                createTable(storeClass.getClass());
                open();
            }

            StringBuilder insertBuilder = new StringBuilder("INSERT INTO ");
            StringBuilder valuesBuilder = new StringBuilder("VALUES( ");
            StringBuilder existsBuilder = new StringBuilder("WHERE ");
            insertBuilder.append(storeClass.getTableName());
            insertBuilder.append("(");

            Iterator<Field> fieldNameFieldIterator = Arrays.asList(storeClass.getClass().getClasses()[1].getFields()).iterator();
            while (fieldNameFieldIterator.hasNext()) {
                FieldName name = (FieldName) fieldNameFieldIterator.next().get(storeClass);
                if (!name.getProgramName().equals("Id")) {
                    insertBuilder.append(name.getSqlName());
                    existsBuilder.append(name.getSqlName()).append("=");
                    Method method = storeClass.getClass().getMethod("get" + name.getProgramName());
                    String content = (String) method.invoke(storeClass);
                    if(content.isEmpty()){
                        content="NULL";
                    }
                    valuesBuilder.append("'").append(content).append("'");
                    existsBuilder.append("'").append(content).append("'");
                    if (fieldNameFieldIterator.hasNext()) {
                        insertBuilder.append(", ");
                        valuesBuilder.append(", ");
                        existsBuilder.append(" AND ");
                    }
                }
            }

            Iterator<Field> foreignKeyFieldIterator = Arrays.asList(storeClass.getClass().getClasses()[0].getFields()).iterator();
            if(!insertBuilder.toString().endsWith("(") && foreignKeyFieldIterator.hasNext() ){
                insertBuilder.append(", ");
                valuesBuilder.append(", ");
            }else {
                insertBuilder.append(")");
                valuesBuilder.append(")");
            }
            while (foreignKeyFieldIterator.hasNext()) {
                ForeignKey<? extends StoreClass> key = (ForeignKey<? extends StoreClass>) foreignKeyFieldIterator.next().get(storeClass);
                insertBuilder.append(key.getSqlName());
                Method method = storeClass.getClass().getMethod("get" + key.getProgramName());
                int content = (Integer) method.invoke(storeClass);
                valuesBuilder.append("'").append(content).append("'");
                if (foreignKeyFieldIterator.hasNext()) {
                    insertBuilder.append(", ");
                    valuesBuilder.append(", ");
                }else {
                    insertBuilder.append(")");
                    valuesBuilder.append(")");
                }
            }

            int id=-1;
            if (checkIfExists){
                System.out.println("CHECK IF EXISTS");
                String sql = "SELECT id FROM "+ storeClass.getTableName() +" "+existsBuilder;
                System.out.println(sql);
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    id = rs.getInt("id");
                    storeClass.setId(id);
                    System.out.println("EXISTS");
                }
            }
            if (id==-1) {
                System.out.println("DOES NOT EXIST OR NOT CHECKED");
                String sql = insertBuilder + " " + valuesBuilder;
                System.out.println(sql);
                stmt.executeUpdate(sql);
                added=true;
                String sql1 = "SELECT id FROM " + storeClass.getTableName() + " ORDER BY id DESC LIMIT 1";
                ResultSet rs = stmt.executeQuery(sql1);
                while (rs.next()) {
                    id = rs.getInt("id");
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
        return added;
    }

    public <T extends StoreClass> void updateObject(T storeClass){
        try {
            open();
            StringBuilder updateBuilder = new StringBuilder("UPDATE ");
            StringBuilder setBuilder = new StringBuilder("SET ");
            StringBuilder whereBuilder = new StringBuilder("WHERE ");
            updateBuilder.append(storeClass.getTableName());
            updateBuilder.append(" ");

            Iterator<Field> fieldNameFieldIterator = Arrays.asList(storeClass.getClass().getClasses()[1].getFields()).iterator();
            while (fieldNameFieldIterator.hasNext()) {
                FieldName name = (FieldName) fieldNameFieldIterator.next().get(storeClass);
                if (name.getProgramName().equals("Id")) {
                    whereBuilder.append("id=").append(storeClass.getId());
                } else {
                    setBuilder.append(name.getSqlName()).append("=");
                    Method method = storeClass.getClass().getMethod("get" + name.getProgramName());
                    setBuilder.append("'").append(method.invoke(storeClass)).append("'");
                    if (fieldNameFieldIterator.hasNext()) {
                        setBuilder.append(", ");
                    }
                }
            }

            Iterator<Field> foreignKeyFieldIterator = Arrays.asList(storeClass.getClass().getClasses()[0].getFields()).iterator();
            if(foreignKeyFieldIterator.hasNext() && !setBuilder.toString().endsWith("SET ")){
                setBuilder.append(", ");
            }
            while (foreignKeyFieldIterator.hasNext()) {
                ForeignKey<? extends StoreClass> key = (ForeignKey<? extends StoreClass>) foreignKeyFieldIterator.next().get(storeClass);
                setBuilder.append(key.getSqlName()).append("=");
                Method method = storeClass.getClass().getMethod("get" + key.getProgramName());
                int temp=(int)method.invoke(storeClass);
                setBuilder.append("'").append(temp).append("'");
                if (foreignKeyFieldIterator.hasNext()) {
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

    public <T extends StoreClass> void createTable(Class<T> storeClass) {
        try{
            T dummyClass = storeClass.getDeclaredConstructor().newInstance();
            open();
            StringBuilder createTableBuilder = new StringBuilder("CREATE TABLE ");
            StringBuilder foreignKeyBuilder = new StringBuilder();
            createTableBuilder.append(dummyClass.getTableName());
            createTableBuilder.append("(");

            Iterator<Field> fieldNameFieldIterator = Arrays.asList(dummyClass.getClass().getClasses()[1].getFields()).iterator();
            while(fieldNameFieldIterator.hasNext()){
                FieldName name = (FieldName) fieldNameFieldIterator.next().get(dummyClass);
                createTableBuilder.append(name.getSqlName()).append(" ");
                createTableBuilder.append(name.getSqlType());
                if(fieldNameFieldIterator.hasNext()){
                    createTableBuilder.append(", ");
                }
            }

            Iterator<Field> foreignKeyFieldIterator = Arrays.asList(dummyClass.getClass().getClasses()[0].getFields()).iterator();
            if(foreignKeyFieldIterator.hasNext() && !createTableBuilder.toString().endsWith("(")){
                createTableBuilder.append(", ");
            }
            if (!foreignKeyFieldIterator.hasNext()){
                createTableBuilder.append(")");
            }
            while (foreignKeyFieldIterator.hasNext()) {
                ForeignKey<? extends StoreClass> key = (ForeignKey<? extends StoreClass>) foreignKeyFieldIterator.next().get(dummyClass);
                createTableBuilder.append(key.getSqlName()).append(" INTEGER");
                foreignKeyBuilder.append("FOREIGN KEY(").append(key.getSqlName()).append(") ");
                foreignKeyBuilder.append("REFERENCES ").append(key.getDummyClazz().getTableName());
                if (foreignKeyFieldIterator.hasNext()) {
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
            close();
        } catch(SQLException se){
            se.printStackTrace();
            System.out.println("CREATE DataBase");
            close();
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
    }

    public void createDataBase() {
        try{
            open();
            String sql = "CREATE DATABASE "+ dbName;
            System.out.println(sql);
            System.out.println("Creating database...");
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        } catch(Exception se){
            se.printStackTrace();
            close();
        }
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    private void open() throws ClassNotFoundException, SQLException {
        if( (conn == null || conn.isClosed()) ){
            conn = DriverManager.getConnection(dbPath);
        }
        if( (stmt == null || stmt.isClosed()) ){
            stmt = conn.createStatement();
            System.out.println("SQL START");
        }
    }

    private void close(){
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

