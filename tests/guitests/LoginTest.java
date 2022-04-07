package guitests;

import controller.WindowManager;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
    private final Loader loader = new Loader("Test_db");

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
        write("TestProfile").push(KeyCode.ENTER);
        ComboBox<Profile> chb = lookup("#cb_profiles").query();
        Assertions.assertThat(contains(chb,"TestProfile")).isTrue();
    }

    private boolean contains(ComboBox<Profile> cb, String name){
        for(Profile p:cb.getItems()){
            if(p.getName().equals(name)){
                return true;
            }
        }
        return false;
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
