package controller;

import model.storeclasses.BankAccount;
import model.storeclasses.Group;
import model.storeclasses.Profile;
import model.storeclasses.StoreClass;

import java.util.ArrayList;

public class ProfileAccountManager {

    private static ArrayList<Profile> profiles = new ArrayList<>();
    private static Profile currentAccount;

    public static void addProfile(Profile acc){
        profiles.add(acc);
    }

    public static ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public static void setProfiles(ArrayList<Profile> profiles) {
        ProfileAccountManager.profiles = profiles;
    }

    public static Profile getCurrentAccount() {
        return currentAccount;
    }

    public static void setCurrentAccount(Profile currentAccount) {
        ProfileAccountManager.currentAccount = currentAccount;
    }

    public static ArrayList<? extends StoreClass> getAllProfileList(StoreClass store) {
        if (store instanceof BankAccount) {
            return currentAccount.getBankAccounts();
        } else if (store instanceof Group) {
            return currentAccount.getGroups();
        } else {
            return new ArrayList<>();
        }
    }
}
