package model;

import controller.*;
import javafx.application.Application;
import javafx.stage.Stage;
import model.storeclasses.Profile;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    public enum windows {Start,Account,NewAccount,NewTransaction,RenameWindow,TransactionWindow};
    public static Stage primaryStage;
    public static Stage secStage;

    public enum types {Lastschrift, Gutschrift, Gehalt, Überweisung}


    // *** Controller ***
    public static IOController ioController;
    public static EditController editController;

    @Override
    public void start(Stage primaryStage) throws IOException{
        Main.primaryStage =primaryStage;

        ioController = new IOController();
        editController = new EditController();

        ArrayList<Profile> profiles = DatabaseController.loadStoreClass(Profile.class);
        ProfileAccountManager.setProfiles(profiles);

        WindowManager.changeSceneTo(windows.Start);
        primaryStage.setTitle("Aurum Observa");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
