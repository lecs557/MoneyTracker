package model;

import controller.AccountManager;
import controller.IOController;
import controller.WindowManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Account  currentAccount;
    public static WindowManager windowManager;
    public static AccountManager accountManager;
    public static IOController ioController;
    public static Stage stage;
    public static Pane contentPane;
    public static Stage newAccStage;
    public enum windows {Start,Account};

    @Override
    public void start(Stage primaryStage) throws IOException{
        stage=primaryStage;
        windowManager=new WindowManager();
        accountManager= new AccountManager();
        ioController = new IOController();
        accountManager.addAcc(new Account("abc","",""));
        accountManager.addAcc(new Account("def","",""));
        Parent root = FXMLLoader.load(getClass().getResource("/view/BaseWindow.fxml"));
        primaryStage.setTitle("Bilanz");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static Account getCurrentAccount() {
        return currentAccount;
    }

    public static void setCurrentAccount(Account currentAccount) {
        Main.currentAccount = currentAccount;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
