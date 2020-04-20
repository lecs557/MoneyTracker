package controller;

import model.Account;
import model.Main;

import java.io.File;
import java.io.IOException;

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

    public void loadAll() {

    }

    public void loadAccount() {

    }
}

