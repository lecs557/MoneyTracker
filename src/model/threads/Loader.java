package model.threads;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import model.Account;
import model.Main;
import model.Transaction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Loader extends Thread {

    private String name;
    private String path;
    private SimpleBooleanProperty running = new SimpleBooleanProperty();

    public Loader(String name, String path) {
        this.name = name;
        this.path = path;
    }

    @Override
    public void run() {
        running.set(true);
        Account loadAccount = new Account(name);
        Main.currentAccount = loadAccount;
        try {
            FileReader loadedFile = new FileReader(path);
            Main.accountManager.addAcc(loadAccount);
            loadAccount.setPath(path.replace("/"+name+".konto",""));
            int temp = loadedFile.read();
            String cur="";
            int j=0;
            while (temp!=-1) {
                if (temp == ';') {
                    j++;
                    loadAccount.addTransaction(createTransaction(cur));
                    cur = "";
                } else {
                    cur += (char) temp;
                }
                temp = loadedFile.read();
            }
        } catch (FileNotFoundException e) {
            System.out.println(path+" wurde nicht gefunden.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(path+" kann nicht gelesen werden.");
            e.printStackTrace();
        }
        Platform.runLater (() -> Main.windowManager.openWindow(Main.windows.Account));
        running.set(false);

    }

    private Transaction createTransaction(String cur) {
        String[] transactionArray = cur.split(":");
        String a = transactionArray[0];
        String b = transactionArray[1];
        String c = transactionArray[transactionArray.length-1];
        if(a==null || b==null || c==null){
            return new Transaction("","cur","0");
        } else {
            return new Transaction(a,b,c);
        }
    }

    public SimpleBooleanProperty isRunningProperty() {
        return running;
    }
}
