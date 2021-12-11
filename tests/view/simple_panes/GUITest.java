package view.simple_panes;

import controller.WindowManager;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.AppStart;
import model.storeclasses.Transaction;
import model.threads.Loader;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class GUITest extends ApplicationTest {

    private final Loader loader = new Loader(true);

    @Override
    public void start(Stage stage) throws Exception {
        AppStart.primaryStage = stage;
        stage.show();
        WindowManager.initialize();
        WindowManager.getInstance().changeSceneTo(AppStart.windows.StartLoading);
        loader.start();
    }

    @Test
    public void addProfileTest(){
        clickOn("#btn_addProfile");
        sleep(5000);

    }
}