package model.runnables;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.Account;
import model.Main;
import model.Transaction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Loader implements Runnable {

    private String name;
    private String path;
    private SimpleIntegerProperty count = new SimpleIntegerProperty(0);
    private SimpleStringProperty current = new SimpleStringProperty();

    public Loader(String name, String path) {
        this.name = name;
        this.path = path;
    }

    @Override
    public void run() {
        Account loadAccount = new Account(name);
        FileReader loadedFile = null;
        try {
            loadedFile = new FileReader(path);
            Main.accountManager.addAcc(loadAccount);
            loadAccount.setPath(path.replace("/"+name+".konto",""));
            int temp = loadedFile.read();
            String cur="";
            int j=0;
            while (temp!=-1) {
                if (temp == ';') {
                    j++;
                    count.set(j);
                    current.set(cur);
                    loadAccount.addTransaction(createTransaction(cur));
                    cur = "";
                } else {
                    cur += (char) temp;
                }
                temp = loadedFile.read();
            }
            Main.currentAccount = loadAccount;
            Platform.runLater(() -> Main.windowManager.openWindpw(Main.windows.Account));
        } catch (FileNotFoundException e) {
            System.out.println(path+" wurde nicht gefunden.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(path+" kann nicht gelesen werden.");
            e.printStackTrace();
        }
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

    public SimpleIntegerProperty countProperty() {
        return count;
    }

    public SimpleStringProperty currentProperty() {
        return current;
    }
}
