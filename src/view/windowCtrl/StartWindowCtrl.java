package view.windowCtrl;


import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import model.Account;
import model.Main;

import java.io.IOException;


public class StartWindowCtrl extends BaseWindowCtrl {

    public ListView<Account> lv_accounts;
    public Button btn_newAcc;

    public void initialize() throws IOException {
        visibility();

        lv_accounts.setItems(Main.accountManager.getAccounts());
        lv_accounts.getItems().addListener((ListChangeListener<? super Account>) change -> visibility());
        lv_accounts.setCellFactory(lambda -> {
            ListCell<Account> cell = new ListCell<Account>() {
                @Override
                protected void updateItem(Account item, boolean empty) {
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
                        Main.setCurrentAccount(cell.getItem());
                        openAccount();
                    }
                }
            });
            return cell;
        });
    }

    public void onNewAcc(){
        Main.windowManager.showNewAccountStage();
    }

    public void visibility(){
        if (Main.accountManager.getAccounts().size()>0){
            lv_accounts.setVisible(true);
            btn_newAcc.setVisible(false);
        } else {
            lv_accounts.setVisible(false);
            btn_newAcc.setVisible(true);
        }
    }

}
