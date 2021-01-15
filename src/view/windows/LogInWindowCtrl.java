package view.windows;


import controller.DatabaseController;
import controller.ProfileAccountManager;
import controller.WindowManager;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import model.storeclasses.Profile;
import model.Main;
import view.simple_panes.CreateNew;


public class LogInWindowCtrl extends BaseWindowCtrl {

    public ChoiceBox<Profile> chb_profiles;

    public void initialize() {
        chb_profiles.getItems().addAll(DatabaseController.computeStoreClasses(new Profile(),""));
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

        Profile.setItemRegion(() -> chb_profiles.getItems() );
    }

    public void ok(ActionEvent actionEvent) {
        Profile selected = chb_profiles.getSelectionModel().getSelectedItem();
        if(selected!=null){
            ProfileAccountManager.setupProfile(selected);
            WindowManager.changeSceneTo(Main.windows.Overview);
        }
    }

    public void addProfile(ActionEvent actionEvent) {
        CreateNew<Profile> createNew = new CreateNew<>(new Profile(), false);
        WindowManager.openStageOf(createNew);
    }
}
