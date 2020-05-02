package controller;

import javafx.collections.ObservableList;
import model.Account;
import model.Main;
import model.Transaction;

import java.io.*;
import java.util.ArrayList;

public class IOController {

    public void saveAll(String path) throws IOException {
        File folder = new File(path);
        folder.mkdir();
        String pth = folder.getAbsolutePath();
        for (Account acc : Main.accountManager.getAccounts()) {
            int j=0;
            FileWriter accountFile = new FileWriter(pth + "/"+ acc.getName()+".konto");
            for (ObservableList<Transaction> olt:acc.getYears_Transaction()) {
                for (Transaction t:olt) {
                    j++;
                    accountFile.append(t.getDate_S() + ":" + t.getReason() + ":" + t.getBetrag() + ";");
                }
            }
            accountFile.close();
            Main.logController.addLog("GESPEICHERT: "+acc.getName()+" -> "+j+" Transactionen");
        }
    }

    public void saveAccount() {

    }

    public Account load(String name, String path) throws IOException {
        Account loadAccount = new Account(name);
        FileReader loadedFile = new FileReader(path);
        Main.accountManager.addAcc(loadAccount);
        int temp = loadedFile.read();
        String cur="";
        int j=0;
        while (temp!=-1){
            if (temp==';') {
                j++;
                loadAccount.addTransaction(createTransaction(cur));
                cur="";
            } else {
                cur+=(char) temp;
            }
            temp=loadedFile.read();
        }
        Main.logController.addLog("GELADEN: "+name+" -> "+j+" Transactionen");
        return loadAccount;
    }

    private Transaction createTransaction(String cur) {
        String a = cur.split(":")[0];
        String b = cur.split(":")[1];
        String c = cur.split(":")[2];
        if(a==null || b==null || c==null){
          return new Transaction("","cur","0");
        } else {
            return new Transaction(a,b,c);
        }
    }
}

