package view.windows;

import controller.DatabaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.storeclasses.Profile;
import model.Main;

public class NewAccountWindowCtrl {

    @FXML
    private TextField tb_name;

    @FXML
    private TextField tb_iban;

    @FXML
    private TextField tb_bic;

    @FXML
    private Button btn_ok;

    @FXML
    private Button btn_close;

    public void initialize() {
        btn_ok.setDisable(true);
        tb_name.setOnKeyTyped(keyEvent -> btn_ok.setDisable(tb_name.getText().trim().isEmpty()));
    }

    @FXML
    void close(ActionEvent event) {
        Main.secStage.close();
    }

    @FXML
    void ok(ActionEvent event) {
        Profile profile = new Profile();
        profile.setName(tb_name.getText());
        profile.setId(DatabaseController.storeObject(profile));
        Main.accountManager.addProfile(profile);
        Main.secStage.close();
    }

}
