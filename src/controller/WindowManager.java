package controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Main;

import java.io.IOException;

public class WindowManager {

    private static SimpleBooleanProperty loadWindow = new SimpleBooleanProperty(false);

    public static void changeSceneTo(Main.windows window) {
        try {
            loadWindow.set(true);
            Parent parent = FXMLLoader.load(WindowManager.class.getResource("/view/windows/"+window.name()+".fxml"));
            Main.primaryStage.setScene(new Scene(parent));
            loadWindow.set(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openStageOf(Main.windows window){
        try {
            Main.secStage =new Stage();
            loadWindow.set(true);
            Parent root = FXMLLoader.load(WindowManager.class.getResource("/view/windows/"+window.name()+".fxml"));
            Main.secStage.setScene(new Scene(root));
            loadWindow.set(false);
            Main.secStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SimpleBooleanProperty loadWindowProperty() {
        return loadWindow;
    }
}
