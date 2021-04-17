package model;

import controller.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class AppStart extends Application {

    public enum windows {LogIn, Overview ,NewAccount,NewTransaction,RenameWindow,TransactionWindow};
    public static Stage primaryStage;
    public static Stage secStage;

    public enum types {Lastschrift, Gutschrift, Gehalt, Überweisung}

    @Override
    public void start(Stage primaryStage) throws IOException{
        AppStart.primaryStage = primaryStage;

        WindowManager.changeSceneTo(windows.LogIn);
        primaryStage.setTitle("Aurum Observa");

        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
