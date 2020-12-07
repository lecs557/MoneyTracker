package view.windows;


import controller.ProfileAccountManager;
import controller.WindowManager;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import model.storeclasses.Profile;
import model.Main;
import model.threads.Loader;


public class LogInWindowCtrl extends BaseWindowCtrl {

    public ChoiceBox<Profile> chb_profiles;

    public void initialize() {
        chb_profiles.getItems().addAll(ProfileAccountManager.getProfiles());
        chb_profiles.setConverter(new StringConverter<Profile>() {
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
    }

    public void ok(ActionEvent actionEvent) {
        Profile selected = chb_profiles.getSelectionModel().getSelectedItem();
        if(selected!=null){
            ProfileAccountManager.setCurrentAccount(selected);
            WindowManager.changeSceneTo(Main.windows.Overview);
        }
    }
}
