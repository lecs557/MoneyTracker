package guitests;

import controller.WindowManager;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.AppStart;
import model.storeclasses.Profile;
import model.threads.Loader;
import org.junit.jupiter.api.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationTest;

class LoginTest extends ApplicationTest {

    private boolean stop = false;
    private final Loader loader = new Loader("Test_login");

    @Override
    public void start(Stage stage) throws Exception {
        AppStart.primaryStage = stage;
        stage.show();
        WindowManager.initialize();
        WindowManager.getInstance().changeSceneTo(AppStart.windows.StartLoading);
        loader.start();
        loader.join();
    }

    @Test
    public void addProfileTest(){
        clickOn("#btn_addProfile");
        write("TestProfile").push(KeyCode.TAB).push(KeyCode.ENTER);
        ChoiceBox<Profile> chb = lookup("#chb_").query();
        Assertions.assertThat(chb).hasChild("TestProfile");
    }

    @Test
    public void free(){
        AppStart.primaryStage.addEventHandler(KeyEvent.KEY_PRESSED,keyEvent -> {
            if(keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.Q)){
                stop = true;
            }
        });

        while (!stop){
            sleep(2000);
        }
    }

}
