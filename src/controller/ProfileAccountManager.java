package controller;

import model.Profile;

import java.util.ArrayList;

public class ProfileAccountManager {

    private ArrayList<Profile> accounts = new ArrayList();

    public void addProfile(Profile acc){
        accounts.add(acc);
    }

    public ArrayList<Profile> getAccounts() {
        return accounts;
    }
}
