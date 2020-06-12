package controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Main;

import java.io.IOException;

public class WindowManager {

    private SimpleBooleanProperty loadWindow = new SimpleBooleanProperty(false);

    public void openWindow(Main.windows windows) {
        try {
            loadWindow.set(true);
            Parent parent = FXMLLoader.load(getClass().getResource("/view/"+windows.name()+".fxml"));
            Main.stage.setScene(new Scene(parent));
            loadWindow.set(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showNewAccountStage(){
        Main.newStage=new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/NewAccount.fxml"));
            Main.newStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.newStage.show();
    }

    public void showNewTransactionStage(){
        Main.newStage=new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/NewTransaction.fxml"));
            Main.newStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.newStage.show();
    }

    public void showRename(){
        Main.newStage=new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/RenameWindow.fxml"));
            Main.newStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.newStage.show();
    }

    public SimpleBooleanProperty loadWindowProperty() {
        return loadWindow;
    }
}
