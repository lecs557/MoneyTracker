package model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import view.simple_panes.SumTable;
import view.simple_panes.TransactionChart;
import view.simple_panes.TransactionTable;

import java.util.ArrayList;

public class Profile extends StoreClass {

    private int id;
    private String name;

    private ArrayList<BankAccount> bankAccounts;

    public Profile(String name)  {
        this.name = name;

    }

    public String getName() {
        return name;
    }


    @Override
    public String getTableName() {
        return "Profiles";
    }

    @Override
    public ArrayList<FieldName> getFieldNames() {
        ArrayList<FieldName> fieldNames = new ArrayList<FieldName>();
        fieldNames.add(new FieldName("id", "id","int NOT NULL AUTO_INCREMENT"));
        fieldNames.add(new FieldName("name", "name","TEXT"));
        return fieldNames;
    }
}

