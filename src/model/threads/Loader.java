package model.threads;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import model.Account;
import model.Main;
import model.Transaction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Loader extends Thread {

    private String name;
    private String path;
    private SimpleStringProperty count = new SimpleStringProperty();
    private SimpleStringProperty current = new SimpleStringProperty();
    private SimpleBooleanProperty running = new SimpleBooleanProperty();

    public Loader(String name, String path) {
        this.name = name;
        this.path = path;
    }

    @Override
    public void run() {
        running.set(true);
        Account loadAccount = new Account(name);
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
                    int finalJ = j;
                    Platform.runLater(() -> count.set(finalJ +""));
                    String finalCur = cur;
                    Platform.runLater(() -> current.set(finalCur));
                    loadAccount.addTransaction(createTransaction(cur));
                    cur = "";
                    Thread.sleep(300);
                } else {
                    cur += (char) temp;
                }
                temp = loadedFile.read();
            }
            Main.currentAccount = loadAccount;
            Platform.runLater(() -> Main.windowManager.openWindow(Main.windows.Account));
        } catch (FileNotFoundException e) {
            System.out.println(path+" wurde nicht gefunden.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(path+" kann nicht gelesen werden.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        running.set(false);
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

    public SimpleStringProperty countProperty() {
        return count;
    }

    public SimpleStringProperty currentProperty() {
        return current;
    }

    public SimpleBooleanProperty isRunningProperty() {
        return running;
    }

    public String getTransactionName() {
        return name;
    }
}
