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
import java.time.LocalDate;

public class Main extends Application {

    public static final String DESTINATION = "Test_Kontos";
    public static Account  currentAccount;
    public static WindowManager windowManager;
    public static AccountManager accountManager;
    public static IOController ioController;
    public static Stage stage;
    public static Stage newAccStage;
    public enum windows {Start,Account};

    @Override
    public void start(Stage primaryStage) throws IOException{
        stage=primaryStage;
        windowManager=new WindowManager();
        accountManager= new AccountManager();
        ioController = new IOController();
//        Account a = new Account("a","","");
//        a.addTransaction(new Transaction(LocalDate.now(),"bbb",50));
//        currentAccount = a;
        Parent root = FXMLLoader.load(getClass().getResource("/view/Start.fxml"));
        primaryStage.setTitle("Bilanz");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
