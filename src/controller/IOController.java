package controller;

import model.Account;
import model.Main;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class IOController {

    public void saveAll(String path) throws IOException {
        File folder = new File(path);
        folder.mkdir();
        String pth = folder.getAbsolutePath();
        for (Account acc : Main.accountManager.getAccounts()) {
            File file = new File(pth + "/"+ acc.getName()+".txt");
            file.createNewFile();

        }
    }

    public void saveAccount() {

    }

    public void loadAll(String path) {
        File folder = new File(path);

        for(String pth: Objects.requireNonNull(folder.list())){
            String name=pth.replace(".txt","");
            Account loadAccount = new Account(name,"","");
            Main.accountManager.addAcc(loadAccount);

            // TODO LOAD COMPLETE


        }

    }

    public void loadAccount(String path) {


    }
}

