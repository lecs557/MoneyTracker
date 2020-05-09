package view.customized_Panes;

import javafx.stage.DirectoryChooser;
import model.Main;

import java.io.File;

public class ViewUtils {

    public static void setPath() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(Main.stage);
        Main.currentAccount.setPath(file.getAbsolutePath());
    }
}
