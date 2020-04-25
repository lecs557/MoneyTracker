package view.windowCtrl;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import model.Main;

import java.io.IOException;

public class BaseWindowCtrl {

    public Menu m_file;
    public MenuItem mi_save;
    public MenuItem mi_newAcc;
    public MenuItem mi_load;
    public MenuItem mi_close;
    public Pane contentPane;

    public void initialize() throws IOException {

    }

    public void close(ActionEvent event) {
        System.exit(0);
    }

    public void openNewAccount(ActionEvent event) {
        Main.windowManager.showNewAccountStage();
    }

    public void openAccount() {
        Main.windowManager.openContent(Main.windows.Account);
    }

    public void openStart(){
        Main.windowManager.openContent(Main.windows.Start);
    }

    public void save(ActionEvent event) throws IOException {
        Main.ioController.saveAll(Main.DESTINATION);
    }

    public void load() throws IOException {
        Main.ioController.loadAll(Main.DESTINATION);
    }
}
