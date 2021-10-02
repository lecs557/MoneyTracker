package controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.AppStart;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class WindowManager {
    
    private static WindowManager singleton;
    
    private WindowManager(){}
    
    public static void initialize(){
        singleton = new WindowManager();
    }
    
    public static WindowManager getInstance(){
        if (singleton == null) {
            initialize();
        }
        return singleton;
    }
        

    private final SimpleBooleanProperty loadWindow = new SimpleBooleanProperty(false);

    public void changeSceneTo(AppStart.windows window) {
        try {
            URL url = getClass().getResource("/view/windows/" + window.name() + ".fxml");
            if (url != null) {
                loadWindow.set(true);
                Parent parent = FXMLLoader.load(url);
                AppStart.primaryStage.setScene(new Scene(parent));
                AppStart.primaryStage.centerOnScreen();
                loadWindow.set(false);
            } else{
                System.out.println("Warning: Url is null  -  "+window.name());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openStageOf(AppStart.windows window){
        try {
            URL url = getClass().getResource("/view/windows/" + window.name() + ".fxml");
            if (url != null) {
                AppStart.secStage =new Stage();
                loadWindow.set(true);
                Parent root = FXMLLoader.load(url);
                AppStart.secStage.setScene(new Scene(root));
                loadWindow.set(false);
                AppStart.secStage.show();
            } else{
                System.out.println("Warning: Url is null  -  "+window.name());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openStageOf(Region createNew){
            AppStart.secStage =new Stage();
            loadWindow.set(true);
            AppStart.secStage.setScene(new Scene(createNew));
            AppStart.secStage.centerOnScreen();
            loadWindow.set(false);
            AppStart.secStage.show();
    }

    public SimpleBooleanProperty loadWindowProperty() {
        return loadWindow;
    }
}
