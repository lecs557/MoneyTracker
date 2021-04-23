package view.simple_panes;

import controller.WindowManager;
import javafx.scene.control.ChoiceBox;
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

public class PaneManager {

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

    public static <T extends StoreClass> AnchorPane makeListAnchorPane(T storeClass){
        try {

            AnchorPane ap_cell = new AnchorPane();
            String labelText = (String) storeClass.getClass().getMethod("get"+storeClass.getChoiceBoxMethodName()).invoke(storeClass);
            Label lbl_itemText = new Label(labelText);
            HBox hb_iconContainer = new HBox();
            ImageView iv_edit = new ImageView(new Image("/ressources/icons/pencil.png"));
            ImageView iv_garbage = new ImageView(new Image("/ressources/icons/garbage.png"));
            iv_edit.setFitHeight(40);
            iv_edit.setPreserveRatio(true);
            iv_garbage.setFitHeight(40);
            iv_garbage.setPreserveRatio(true);

            hb_iconContainer.getChildren().addAll(iv_edit,iv_garbage);
            ap_cell.getChildren().addAll(lbl_itemText,hb_iconContainer);
            hb_iconContainer.setVisible(false);
            AnchorPane.setLeftAnchor(lbl_itemText,0d);
            AnchorPane.setRightAnchor(hb_iconContainer,0d);

            ap_cell.setOnMouseEntered(mouseEvent -> hb_iconContainer.setVisible(true));
            ap_cell.setOnMouseExited(mouseEvent -> hb_iconContainer.setVisible(false));
            iv_edit.setOnMouseClicked(mouseEvent -> WindowManager.openStageOf(new CreateNew<T>(storeClass,true)));
            iv_garbage.setOnMouseClicked(mouseEvent -> WindowManager.openStageOf(new CreateNew<T>(storeClass,true)));

            return ap_cell;

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
