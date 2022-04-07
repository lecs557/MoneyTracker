package view.simple_panes;

import controller.WindowManager;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import model.AppStart;
import model.storeclasses.StoreClass;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class PaneUtils {

    public static List<File> browsePdfFiles(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("PDF auswählen");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDFFiles", "*.pdf"));
        return chooser.showOpenMultipleDialog(AppStart.primaryStage);
    }

    public static File browseOnePdfFile() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("PDF auswählen");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDFFiles", "*.pdf"));
        return chooser.showOpenDialog(AppStart.primaryStage);
    }

    public static void selectFirst(ComboBox<?> comboBox){
        if(!comboBox.getItems().isEmpty()){
            comboBox.getSelectionModel().selectFirst();
        }
    }


}
