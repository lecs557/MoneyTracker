package model;

import controller.*;
import javafx.application.Application;
import javafx.stage.Stage;
import model.threads.Loader;
import view.simple_panes.TransactionChart;

import java.io.IOException;

public class AppStart extends Application {

    public enum windows {StartLoading,LogIn, Overview};
    public static Stage primaryStage;
    public static Stage secStage;
    public static Loader loader = new Loader();

    @Override
    public void start(Stage primaryStage){
        AppStart.primaryStage = primaryStage;
        primaryStage.setTitle("Money Tracker");
        WindowManager.initialize();
        WindowManager.getInstance().changeSceneTo(windows.StartLoading);
        primaryStage.show();
        loader.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
