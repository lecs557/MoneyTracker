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
            Parent parent = FXMLLoader.load(getClass().getResource("../view/"+windows.name()+".fxml"));
            Main.stage.setScene(new Scene(parent));
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
