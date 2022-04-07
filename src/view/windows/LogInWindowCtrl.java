package view.windows;


import controller.DatabaseController;
import controller.ProfileAccountManager;
import controller.ViewController;
import controller.WindowManager;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import model.storeclasses.Group;
import model.storeclasses.Profile;
import model.AppStart;
import model.storeclasses.StoreClass;
import view.simple_panes.CreateNew;
import view.simple_panes.PaneUtils;

import java.lang.reflect.InvocationTargetException;


public class LogInWindowCtrl extends BaseWindowCtrl {

    public ComboBox<Profile> cb_profiles;
    public Button btn_edit;

    public void initialize() {
        ImageView iv_edit = new ImageView(new Image(getClass().getResource("/resources/icons/pencil.png").toString()));
        iv_edit.getStyleClass().add("iv-edit");
        iv_edit.setFitHeight(50);
        iv_edit.setPreserveRatio(true);
        btn_edit.getStyleClass().add("btn-edit");
        btn_edit.setGraphic(iv_edit);
        btn_edit.setOnMouseClicked(mouseEvent -> WindowManager.getInstance().openStageOf(new CreateNew<Profile>(cb_profiles.getSelectionModel().getSelectedItem(),true)));

        cb_profiles.getItems().addAll(DatabaseController.getInstance().computeStoreClasses(new Profile(),""));
        cb_profiles.setConverter(new StringConverter<Profile>() {
            @Override
            public String toString(Profile profile) {
                if (profile != null) {
                    return profile.getName();
                }
                return "";
            }

            @Override
            public Profile fromString(String s) {
                return null;
            }
        });
        PaneUtils.selectFirst(cb_profiles);
        ViewController.getInstance().setCb_profiles(cb_profiles);
    }

    public void login(ActionEvent actionEvent) {
        Profile selected = cb_profiles.getSelectionModel().getSelectedItem();
        if(selected!=null){
            ProfileAccountManager.getInstance().setupProfile(selected);
            WindowManager.getInstance().changeSceneTo(AppStart.windows.Overview);
        }
    }

    public void addProfile(ActionEvent actionEvent) {
        CreateNew<Profile> createNew = new CreateNew<>(new Profile(), false);
        WindowManager.getInstance().openStageOf(createNew);
    }

    private AnchorPane makeListAnchorPane(Profile profile){
        try {
            AnchorPane ap_cell = new AnchorPane();
            ap_cell.getStyleClass().add("ap-content");
            String labelText = (String) profile.getClass().getMethod("get"+profile.getChoiceBoxMethodName()).invoke(profile);
            Label lbl_itemText = new Label(labelText);
            lbl_itemText.getStyleClass().add("lbl-itemText");
            HBox hb_iconContainer= new HBox();
            hb_iconContainer.getStyleClass().add("hb-iconContainer");
            ImageView iv_edit = new ImageView(new Image(getClass().getResource("/resources/icons/pencil.png").toString()));
            iv_edit.getStyleClass().add("iv-edit");
            iv_edit.setFitHeight(10);
            iv_edit.setPreserveRatio(true);
            Button btn_edit = new Button();
            btn_edit.getStyleClass().add("btn-edit");
            btn_edit.setGraphic(iv_edit);

            hb_iconContainer.getChildren().add(btn_edit);
            ap_cell.getChildren().addAll(lbl_itemText,hb_iconContainer);
            hb_iconContainer.setVisible(false);
            AnchorPane.setLeftAnchor(lbl_itemText,0d);
            AnchorPane.setRightAnchor(hb_iconContainer,0d);

            ap_cell.setOnMouseEntered(mouseEvent -> hb_iconContainer.setVisible(true));
            ap_cell.setOnMouseExited(mouseEvent -> hb_iconContainer.setVisible(false));
            btn_edit.setOnMouseClicked(mouseEvent -> WindowManager.getInstance().openStageOf(new CreateNew<Profile>(profile,true)));

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
