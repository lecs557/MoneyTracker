package guitests;

import controller.WindowManager;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.AppStart;
import model.storeclasses.BankAccount;
import model.threads.Loader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationTest;

class OverViewTest extends ApplicationTest {

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
    @BeforeEach
    public void openWindow() {
        clickOn("#btn_login");
    }
    @Test
    public void addBankAccountTest(){
        clickOn(".bank .btn-add");
        write("TestBank").push(KeyCode.ENTER);
        ListView<BankAccount> bal = lookup(".bank .list-view").query();
        Assertions.assertThat(bal).hasChild("TestBank");
    }
    @Test
    public void addGroupTest(){
        clickOn(".group .btn-add");
        write("TestGroup");
        clickOn(".color").push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.SPACE);
        push(KeyCode.ENTER);
        ListView<BankAccount> bal = lookup(".group .list-view").query();
        Assertions.assertThat(bal).hasChild("TestGroup");
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
