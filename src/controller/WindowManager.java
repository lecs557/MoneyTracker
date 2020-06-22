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

    public void openWindow(Main.windows window) {
        try {
            loadWindow.set(true);
            Parent parent = FXMLLoader.load(getClass().getResource("/view/windows/"+window.name()+".fxml"));
            Main.stage.setScene(new Scene(parent));
            loadWindow.set(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage(Main.windows window){
        try {
            Main.newStage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/windows/"+window.name()+".fxml"));
            Main.newStage.setScene(new Scene(root));
            Main.newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SimpleBooleanProperty loadWindowProperty() {
        return loadWindow;
    }
}
