package model;

import controller.*;
import javafx.application.Application;
import javafx.stage.Stage;
import view.simple_panes.TransactionChart;

import java.io.IOException;

public class AppStart extends Application {

    public enum windows {StartLoading,LogIn, Overview};
    public static Stage primaryStage;
    public static Stage secStage;

    @Override
    public void start(Stage primaryStage){
        AppStart.primaryStage = primaryStage;
        primaryStage.setTitle("Aurum Observa");
        WindowManager.changeSceneTo(windows.StartLoading);
        primaryStage.show();

        //WindowManager.changeSceneTo(windows.LogIn);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
