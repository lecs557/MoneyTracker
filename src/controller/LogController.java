package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Account;

public class LogController{

    private ObservableList<String> logs = FXCollections.observableArrayList();

    public void addLog(String log){
        logs.add(log);
    }

    public ObservableList<String> getLogs() {
        return logs;
    }
}
