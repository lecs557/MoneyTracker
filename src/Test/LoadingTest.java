import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static javafx.application.Application.launch;
import static org.junit.jupiter.api.Assertions.*;


public class LoadingTest extends ApplicationTest {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
       primaryStage = this.primaryStage;
    }

    @Test
    void newAccountWindow() {
        Assertions.assertDoesNotThrow(() -> {
            Parent root = FXMLLoader.load(getClass().getResource("/view/NewAccount.fxml"));
        });
    }

    @Test
    void startWindow() {
        Assertions.assertDoesNotThrow(() -> {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Start.fxml"));
        });
    }
}
