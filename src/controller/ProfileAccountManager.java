package controller;

import model.Profile;

import java.util.ArrayList;

public class ProfileAccountManager {

    private ArrayList<Profile> profiles = new ArrayList();

    public void addProfile(Profile acc){
        profiles.add(acc);
    }

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }
}
