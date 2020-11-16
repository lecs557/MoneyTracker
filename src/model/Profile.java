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

public class Profile {

    private String name;
    private String filePath;

    private ArrayList<BankAccount> bankAccounts;

    public Profile(String name)  {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


}

