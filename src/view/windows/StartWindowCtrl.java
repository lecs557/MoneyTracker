package view.windows;


import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import model.storeclasses.Profile;
import model.Main;
import model.threads.Loader;


public class StartWindowCtrl extends BaseWindowCtrl {

    public ListView<Profile> lv_accounts;
    public Button btn_createProfile;
    public Pane loadPane;
    public Label lbl_error;

    private  Loader load;

    public void initialize() {

        visibility();
        Main.ioController.loadRunningProperty().addListener((observableValue, aBoolean, t1) -> {
            if(t1){
                loadPane.setVisible(true);
            }
        });

        Main.windowManager.loadWindowProperty().addListener((observableValue, aBoolean, t1) -> {
            if(t1){
                loadPane.setVisible(true);
            }else {
                loadPane.setVisible(false);
            }
        });

        lv_accounts.getItems().addAll(Main.accountManager.getProfiles());
        lv_accounts.getItems().addListener((ListChangeListener<? super Profile>) change -> visibility());
        lv_accounts.setCellFactory(lambda -> {
            ListCell<Profile> cell = new ListCell<Profile>() {
                @Override
                protected void updateItem(Profile item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item==null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(null);
                        setGraphic(new Label(item.getName()));// TODO mit Scenebuilder
                    }
                }
            };
            cell.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        System.out.println("Double clicked");
                        Main.currentAccount = cell.getItem();
                        openAccount();
                    }
                }
            });
            return cell;
        });
    }

    public void createProfile(){
        Main.windowManager.openStageOf(Main.windows.NewAccount);
    }

    public void visibility(){
        if (Main.accountManager.getProfiles().size()>0){
            lv_accounts.setVisible(true);
            btn_createProfile.setVisible(false);
        } else {
            btn_createProfile.setVisible(true);
            lv_accounts.setVisible(false);
        }

    }

}
