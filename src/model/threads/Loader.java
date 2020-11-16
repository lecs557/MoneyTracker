package model.threads;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import model.Profile;
import model.Group;
import model.Main;
import model.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
        Profile loadAccount = new Profile(name);
        Main.currentAccount = loadAccount;
        try {
            Scanner loadedFile = new Scanner(new File(path));
            Main.accountManager.addProfile(loadAccount);
            loadAccount.setFilePath(path.replace("/"+name+".konto",""));
            loadedFile.useDelimiter(Main.ENDSEPARATOR);
            String cur="";
            int j=0;
            while (loadedFile.hasNext()) {
                cur = loadedFile.next();
                cur = cur.replace(Main.ENDSEPARATOR,"");
                if(cur.startsWith(Main.OPTIONSEPARATOR)){
                   cur = cur.replace(Main.OPTIONSEPARATOR,"");
                    j++;
                }
                if(j==0){
                    loadAccount.addGroup(Group.groupFromString(cur));
                }if(j==1){
                    loadAccount.addTransaction(Transaction.transactionFromString(cur));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(path+" wurde nicht gefunden.");
            e.printStackTrace();
        }
        Platform.runLater (() -> Main.windowManager.changeSceneTo(Main.windows.Account));
        running.set(false);

    }

    public SimpleBooleanProperty isRunningProperty() {
        return running;
    }
}
