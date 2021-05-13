package controller;

import javafx.application.Platform;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import model.storeclasses.*;
import view.simple_panes.TransactionTabPane;
import view.windows.OverviewWindowCtrl;

import java.util.ArrayList;

public class ViewController {

    private static OverviewWindowCtrl o;
    private static ChoiceBox<Profile> chb_profiles;

    public static <T extends StoreClass> void refresh(T storeClass) {
        Platform.runLater(() -> {
            if (storeClass instanceof BankAccount){
                o.vb_bankAccounts.getListView().getItems().setAll(DatabaseController.computeStoreClasses(new BankAccount(),""));
            } else if (storeClass instanceof Profile){
                chb_profiles.getItems().setAll(DatabaseController.computeStoreClasses(new Profile(),""));
            } else if (storeClass instanceof Group){
                o.vb_groups.getListView().getItems().setAll(DatabaseController.computeStoreClasses(new Group(),""));
                ArrayList<Group> groups = new ArrayList<>(o.vb_groups.getListView().getItems());
                o.tl_groupSums.applyGroups(groups);
            } else if (storeClass instanceof Transaction){
                ArrayList<Transaction> transactions =DatabaseController.computeStoreClasses(new Transaction(),Transaction.Variables.date.getSqlName());
                ArrayList<Group> groups = new ArrayList<>(o.vb_groups.getListView().getItems());
                ProfileAccountManager.computeBalance(transactions);
                ProfileAccountManager.computeSum(groups,transactions);
                o.tp_transactions.applyTransactions(transactions,groups);
                o.tl_groupSums.applyGroups(groups);
                o.ch_transaction.applyTransactions(transactions);
            }
        });
    }



    public static void setOverviewWindowCtrl(OverviewWindowCtrl overviewWindowCtrl) {
        ViewController.o = overviewWindowCtrl;
    }

    public static void setChb_profiles(ChoiceBox<Profile> chb_profiles) {
        ViewController.chb_profiles = chb_profiles;
    }
}
