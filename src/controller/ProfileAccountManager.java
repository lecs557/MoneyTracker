package controller;

import model.storeclasses.Profile;

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
}
