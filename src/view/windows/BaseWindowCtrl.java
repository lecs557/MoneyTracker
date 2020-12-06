package view.windows;

import controller.WindowManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.storeclasses.Profile;
import model.Main;

import java.io.File;
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

}
