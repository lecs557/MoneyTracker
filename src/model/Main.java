package model;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Account  currentAccount;
    public static WindowManager windowManager;
    public static AccountManager accountManager;
    public static IOController ioController;
    public static PDFController pdfController;
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
        pdfController = new PDFController();

        Parent root;
        if (getParameters().getUnnamed().size() > 0) {
            String path = getParameters().getUnnamed().get(0);
            String name = path.split("/")[path.split("/").length-1].replace(".konto","");
            currentAccount = ioController.load(name,path);
            root = FXMLLoader.load(getClass().getResource("/view/Account.fxml"));
        } else {
            root = FXMLLoader.load(getClass().getResource("/view/Start.fxml"));
        }


        primaryStage.setTitle("Bilanz");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
