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
                ArrayList<BankAccount> all = db.computeStoreClasses(new BankAccount(),"");
                o.vb_bankAccounts.getListView().getItems().setAll(all);
                ProfileAccountManager.getInstance().setProfilesBankAccounts(all);
            } else if (storeClass instanceof Profile){
                ArrayList<Profile> all = db.computeStoreClasses(new Profile(),"");
                chb_profiles.getItems().setAll(all);
            } else if (storeClass instanceof Group){
                ArrayList<Group> all = db.computeStoreClasses(new Group(),"");
                o.vb_groups.getListView().getItems().setAll(all);
                o.tl_groupSums.applyGroups(all);
                ProfileAccountManager.getInstance().setProfilesGroups(all);
            } else if (storeClass instanceof Transaction){
                ArrayList<Transaction> allTransactions =db.computeStoreClasses(new Transaction(),Transaction.Variables.date.getSqlName());
                ArrayList<Group> allGroups = ProfileAccountManager.getInstance().getProfilesGroups();
                ProfileAccountManager.getInstance().computeBalance(allTransactions);
                ProfileAccountManager.getInstance().computeSum(allGroups,allTransactions);
                o.tp_transactions.applyTransactions(allTransactions,allGroups);
                o.tl_groupSums.applyGroups(allGroups);
                o.ch_transaction.applyTransactions(allTransactions);
                ProfileAccountManager.getInstance().setProfilesTransactions(allTransactions);
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
