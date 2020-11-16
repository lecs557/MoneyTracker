package model.threads;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.*;

import java.util.ArrayList;

public class Renamer extends Thread {

    private SimpleDoubleProperty progressYear = new SimpleDoubleProperty();
    private SimpleDoubleProperty progressTransaction = new SimpleDoubleProperty();
    private SimpleBooleanProperty isRunning = new SimpleBooleanProperty(false);
    private Renames renames;

    public Renamer(Renames renames) {
        this.renames = renames;
    }

    @Override
    public void run() {
        isRunning.set(true);
        Profile acc = Main.currentAccount;
        int i=0;
        int sizeYt = acc.getYears_Transaction().size();
        for (ArrayList<Transaction> yt: Main.currentAccount.getYears_Transaction()){
            i++;
            progressYear.set((double) i/sizeYt);
            int j=0;
            int sizeT = yt.size();
            for(Transaction t: yt){
                if(t.getPurpose().contains(renames.getContains())){
                    t.setPurpose(renames.getRenameTo());
                }
                j++;
                progressTransaction.set((double)j/sizeT);
            }
        }
        Sum a=null;
        Sum b=null;
        for(Sum s:Main.currentAccount.getSums()) {
            if(s.getReason().equals(renames.getRenameTo())){
                a=s;
            }
            if(s.getReason().contains(renames.getContains())){
                b=s;
            }
        }
        if (b==null)
            System.out.println("B is null");
        else {
            if (a == null) {
                b.setReason(renames.getRenameTo());
            } else {
                a.mergesum(b);
            }
            isRunning.set(false);
        }
    }

    public SimpleDoubleProperty progressYearProperty() {
        return progressYear;
    }

    public SimpleDoubleProperty progressTransactionProperty() {
        return progressTransaction;
    }

    public SimpleBooleanProperty runningProperty() {
        return isRunning;
    }

}

