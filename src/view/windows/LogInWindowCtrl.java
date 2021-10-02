package view.windows;


import controller.DatabaseController;
import controller.ProfileAccountManager;
import controller.ViewController;
import controller.WindowManager;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import model.storeclasses.Profile;
import model.AppStart;
import view.simple_panes.CreateNew;
import view.simple_panes.PaneUtils;


public class LogInWindowCtrl extends BaseWindowCtrl {

    public ChoiceBox<Profile> chb_profiles;

    public void initialize() {
        chb_profiles.getItems().addAll(DatabaseController.getInstance().computeStoreClasses(new Profile(),""));
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
        PaneUtils.selectFirst(chb_profiles);
        ViewController.getInstance().setChb_profiles(chb_profiles);
    }

    public void login(ActionEvent actionEvent) {
        Profile selected = chb_profiles.getSelectionModel().getSelectedItem();
        if(selected!=null){
            ProfileAccountManager.getInstance().setupProfile(selected);
            WindowManager.getInstance().changeSceneTo(AppStart.windows.Overview);
        }
    }

    public void addProfile(ActionEvent actionEvent) {
        CreateNew<Profile> createNew = new CreateNew<>(new Profile(), false);
        WindowManager.getInstance().openStageOf(createNew);
    }
}
