package controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.AppStart;

import java.io.IOException;

public class WindowManager {

    private static SimpleBooleanProperty loadWindow = new SimpleBooleanProperty(false);

    public static void changeSceneTo(AppStart.windows window) {
        try {
            loadWindow.set(true);
            Parent parent = FXMLLoader.load(WindowManager.class.getResource("/view/windows/"+window.name()+".fxml"));
            AppStart.primaryStage.setScene(new Scene(parent));
            AppStart.primaryStage.centerOnScreen();
            loadWindow.set(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openStageOf(AppStart.windows window){
        try {
            AppStart.secStage =new Stage();
            loadWindow.set(true);
            Parent root = FXMLLoader.load(WindowManager.class.getResource("/view/windows/"+window.name()+".fxml"));
            AppStart.secStage.setScene(new Scene(root));
            loadWindow.set(false);
            AppStart.secStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openStageOf(Region createNew){
            AppStart.secStage =new Stage();
            loadWindow.set(true);
            Parent root = createNew;
            AppStart.secStage.setScene(new Scene(root));
            AppStart.secStage.centerOnScreen();
            loadWindow.set(false);
            AppStart.secStage.show();
    }

    public static SimpleBooleanProperty loadWindowProperty() {
        return loadWindow;
    }
}
