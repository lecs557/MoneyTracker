package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Account;

import java.util.ArrayList;

public class AccountManager {

    private ObservableList<Account> accounts = FXCollections.observableArrayList();

    public void addAcc(Account acc){
        accounts.add(acc);
    }

    public ObservableList<Account> getAccounts() {
        return accounts;
    }
}
