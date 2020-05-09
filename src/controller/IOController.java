package controller;

import javafx.collections.ObservableList;
import model.Account;
import model.Main;
import model.Transaction;
import model.runnables.Loader;
import model.runnables.Saver;

import java.io.*;

public class IOController {

    Thread load;
    Thread save;

    public void save()  {
       save = new Thread(new Saver());
        save.start();
    }

    public void load(String name, String path) {
        load = new Thread(new Loader(name, path));
        load.start();
    }

}

