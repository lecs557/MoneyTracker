package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Main;

import java.io.IOException;

public class WindowManager {

    public void openContent(Main.windows windows) {
        try {
            Main.contentPane.getChildren().clear();
            Main.contentPane.getChildren().add( FXMLLoader.load(getClass().getResource("../view/"+windows.name()+".fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showNewAccountStage(){
        Main.newAccStage=new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/NewAccount.fxml"));
            Main.newAccStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.newAccStage.show();
    }
}
