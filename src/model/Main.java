package model;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.simple_panes.PDFViewer;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    public enum windows {Start,Account,NewAccount,NewTransaction,RenameWindow,TransactionWindow};
    public static Stage primaryStage;
    public static Stage secStage;

    public enum types {Lastschrift, Gutschrift, Gehalt, Überweisung}
    public static Profile currentAccount;

    // *** Controller ***
    public static WindowManager windowManager;
    public static ProfileAccountManager accountManager;
    public static IOController ioController;
    public static EditController editController;

    @Override
    public void start(Stage primaryStage) throws IOException{
        Main.primaryStage =primaryStage;

        windowManager=new WindowManager();
        accountManager= new ProfileAccountManager();
        ioController = new IOController();
        editController = new EditController();

        //ArrayList<? extends StoreClass> ssdd = DatabaseController.loadStoreClass(Profile.class);
        //accountManager.setProfiles((ArrayList<Profile>) ssdd);

        windowManager.changeSceneTo(windows.Start);
        primaryStage.setTitle("Finanzen");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
