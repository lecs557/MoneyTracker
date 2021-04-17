package view.simple_panes;

import javafx.scene.control.ChoiceBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import model.AppStart;

import java.io.File;
import java.util.List;

public class ViewUtils {


    public static void setPath() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(AppStart.primaryStage);
    }

    public static List<File> browsePdfFiles(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("PDF auswählen");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDFFiles", "*.pdf"));
        return chooser.showOpenMultipleDialog(AppStart.primaryStage);
    }

    public static void selectFirst(ChoiceBox<?> choiceBox){
        if(!choiceBox.getItems().isEmpty()){
            choiceBox.getSelectionModel().selectFirst();
        }
    }
}
