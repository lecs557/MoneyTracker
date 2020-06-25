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

    public void changeSceneTo(Main.windows window) {
        try {
            loadWindow.set(true);
            Parent parent = FXMLLoader.load(getClass().getResource("/view/windows/"+window.name()+".fxml"));
            Main.primaryStage.setScene(new Scene(parent));
            loadWindow.set(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openStageOf(Main.windows window){
        try {
            Main.secStage =new Stage();
            loadWindow.set(true);
            Parent root = FXMLLoader.load(getClass().getResource("/view/windows/"+window.name()+".fxml"));
            Main.secStage.setScene(new Scene(root));
            loadWindow.set(false);
            Main.secStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SimpleBooleanProperty loadWindowProperty() {
        return loadWindow;
    }
}
