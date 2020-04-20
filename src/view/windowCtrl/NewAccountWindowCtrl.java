package view.windowCtrl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.Account;
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
        Main.newAccStage.close();
    }

    @FXML
    void ok(ActionEvent event) {
        Main.accountManager.addAcc(new Account(tb_name.getText(), tb_iban.getText(), tb_bic.getText()));
        Main.newAccStage.close();
    }

}
