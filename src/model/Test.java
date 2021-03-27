package model;

import controller.WindowManager;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import view.simple_panes.BankAccountList;
import view.simple_panes.GroupList;
import view.simple_panes.SumTable;
import view.simple_panes.TransactionTabPane;

import java.io.IOException;

public class Test extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException{

        Region node = new SumTable();
        node.setPrefHeight(200);
        node.setPrefWidth(100);
        primaryStage.setScene(new Scene(node,500,500));
        primaryStage.setTitle("Aurum Observa");

        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
