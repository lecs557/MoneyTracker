package model;

import controller.AccountManager;
import controller.IOController;
import controller.LogController;
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
    public static LogController logController;
    public static Stage stage;
    public static Stage newAccStage;
    public static Stage newTransaction;
    public static Stage logStage;
    public enum windows {Start,Account};

    @Override
    public void start(Stage primaryStage) throws IOException{
        stage=primaryStage;

        windowManager=new WindowManager();
        accountManager= new AccountManager();
        ioController = new IOController();
        logController = new LogController();

        Parent root;
        if (getParameters() != null) {
            String path = getParameters().getUnnamed().get(0);
            String name = path.split("/")[path.split("/").length-1].replace(".konto","");
            currentAccount = ioController.load(name,path);
            root = FXMLLoader.load(getClass().getResource("/view/Account.fxml"));
        } else {
            root = FXMLLoader.load(getClass().getResource("/view/Start.fxml"));
        }
//        Account a = new Account("a","","");
//        a.addTransaction(new Transaction(LocalDate.now(),"bbb",50020));
//        currentAccount = a;

        primaryStage.setTitle("Bilanz");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        logController.addLog("GESTARTET");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
