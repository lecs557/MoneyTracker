package model;

import controller.WindowManager;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.storeclasses.BankAccount;
import view.simple_panes.*;

import java.io.IOException;
import java.util.ArrayList;

public class Test extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException{

        Region node = new TransactionTable(new ArrayList<>());
        node.getStylesheets().add(getClass().getResource("/view/style/tatabpane.css").toString());
        node.setPrefHeight(800);
        node.setPrefWidth(800);
        primaryStage.setScene(new Scene(node));
        primaryStage.setTitle("Aurum Observa");

        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
