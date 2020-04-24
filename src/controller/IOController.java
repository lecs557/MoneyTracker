package controller;

import model.Account;
import model.Main;
import model.Transaction;

import java.io.*;
import java.util.Objects;

public class IOController {

    public void saveAll(String path) throws IOException {
        File folder = new File(path);
        folder.mkdir();
        String pth = folder.getAbsolutePath();
        for (Account acc : Main.accountManager.getAccounts()) {
            FileWriter accountFile = new FileWriter(pth + "/"+ acc.getName()+".txt");
            for (Transaction t:acc.getTransactions()) {
                accountFile.append(t.getDateString()+":"+t.getReason()+":"+t.getBetrag()+";");
            }


        }
    }

    public void saveAccount() {

    }

    public void loadAll(String path) throws IOException {
        File folder = new File(path);

        for(String pth: Objects.requireNonNull(folder.list())){
            String name=pth.replace(".txt","");
            Account loadAccount = new Account(name,"","");
            FileReader loadedFile = new FileReader(path + "/"+ pth);
            Main.accountManager.addAcc(loadAccount);
            int temp = loadedFile.read();
            String cur="";
            while (temp!=-1){
                if (temp==';') {
                    loadAccount.addTransaction(createTransaction(cur));
                    cur="";
                } else {
                    cur+=(char) temp;
                }
                temp=loadedFile.read();
            }



            // TODO LOAD COMPLETE


        }

    }

    public void loadAccount(String path) {


    }

    private Transaction createTransaction(String cur) {
        String a = cur.split(":")[0];
        String b = cur.split(":")[1];
        String c = cur.split(":")[2];
        if(a==null || b==null || c==null){
          return new Transaction(cur,"",0);
        } else {
            try {
                int cInt = Integer.parseInt(c);
                return new Transaction(a,b,cInt);
            } catch (NumberFormatException e) {
                System.out.println("Betrag ist keine Zahl");
                return new Transaction(a,b+"  "+c,0);
            }
        }
    }
}

