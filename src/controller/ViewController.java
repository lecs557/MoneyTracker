package controller;

import javafx.application.Platform;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import model.storeclasses.*;
import view.simple_panes.TransactionTabPane;
import view.windows.OverviewWindowCtrl;

import java.util.ArrayList;

public class ViewController {
    
    private static ViewController singleton;
    
    private ViewController(){}
    
    public static void initialize(){
        singleton = new ViewController();
    }
    
    public static ViewController getInstance(){
        if (singleton == null) {
            initialize();
        }
        return singleton;
    }
        

    private OverviewWindowCtrl o;
    private ChoiceBox<Profile> chb_profiles;

    public <T extends StoreClass> void refresh(T storeClass) {
        DatabaseController db = DatabaseController.getInstance();
        Platform.runLater(() -> {
            if (storeClass instanceof BankAccount){
                o.vb_bankAccounts.getListView().getItems().setAll(db.computeStoreClasses(new BankAccount(),""));
            } else if (storeClass instanceof Profile){
                chb_profiles.getItems().setAll(db.computeStoreClasses(new Profile(),""));
            } else if (storeClass instanceof Group){
                o.vb_groups.getListView().getItems().setAll(db.computeStoreClasses(new Group(),""));
                ArrayList<Group> groups = new ArrayList<>(o.vb_groups.getListView().getItems());
                o.tl_groupSums.applyGroups(groups);
            } else if (storeClass instanceof Transaction){
                ArrayList<Transaction> transactions =db.computeStoreClasses(new Transaction(),Transaction.Variables.date.getSqlName());
                ArrayList<Group> groups = new ArrayList<>(o.vb_groups.getListView().getItems());
                ProfileAccountManager.getInstance().computeBalance(transactions);
                ProfileAccountManager.getInstance().computeSum(groups,transactions);
                o.tp_transactions.applyTransactions(transactions,groups);
                o.tl_groupSums.applyGroups(groups);
                o.ch_transaction.applyTransactions(transactions);
            }
        });
    }



    public void setOverviewWindowCtrl(OverviewWindowCtrl overviewWindowCtrl) {
        this.o = overviewWindowCtrl;
    }

    public void setChb_profiles(ChoiceBox<Profile> chb_profiles) {
        this.chb_profiles = chb_profiles;
    }
}
