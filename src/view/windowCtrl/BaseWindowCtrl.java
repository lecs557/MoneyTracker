package view.windowCtrl;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.Account;
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

    public void openNewAccount(ActionEvent event) {
        Main.windowManager.showNewAccountStage();
    }

    public void openAccount() {
        Main.windowManager.openWindpw(Main.windows.Account);
    }

    public void openStart(){
        Main.windowManager.openWindpw(Main.windows.Start);
    }

    public void save(ActionEvent event) throws IOException {
        Main.ioController.saveAll(Main.DESTINATION);
    }

    public void load() throws IOException {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Konto auswählen");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("KontoFiles","*.konto"));
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File temp = chooser.showOpenDialog(Main.stage);
        boolean exist = false;
        if (temp != null) {
            String name = temp.getName().replace(".konto","");
            for (Account acc:Main.accountManager.getAccounts()){
                if(name==acc.getName()){
                    exist =true;
                }
            }
            if (!exist)
                Main.ioController.load(name,temp.getAbsolutePath());
        }
    }
}
