import controller.AccountManager;
import controller.WindowManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import java.io.IOException;

public class ScenarioTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Main.windowManager=new WindowManager();
        Main.accountManager= new AccountManager();
        Parent root = FXMLLoader.load(getClass().getResource("/view/BaseWindow.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Test
    void createNewAccount() {
        clickOn("#btn_newAcc");
        clickOn("#tb_name").write("TEST1");
        clickOn("#btn_ok");
        clickOn("#m_file").clickOn("#mi_newAcc");
        clickOn("#tb_name").write("TEST2");
        clickOn("#btn_ok");
        Assertions.assertFalse(Main.accountManager.getAccounts().isEmpty());
        doubleClickOn("TEST1");
    }
}
