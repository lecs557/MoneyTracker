package view.windowCtrl;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import model.Account;
import model.Main;
import model.threads.Loader;


public class StartWindowCtrl extends BaseWindowCtrl {

    public ListView<Account> lv_accounts;
    public Button btn_newAcc;
    public GridPane gp_load;
    public Label lbl_load, lbl_element, lbl_count;

    private  Loader load;

    public void initialize() {

        visibility();

        Main.ioController.loadRunningProperty().addListener((observableValue, aBoolean, t1) -> {
            if(t1){
                load = Main.ioController.getLoad();
               Platform.runLater(()-> visibility());
            }else {
                load=null;
            }
        });



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
                        Main.currentAccount = cell.getItem();
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
        if (load!=null){
            lbl_load.setText(load.getTransactionName());
            lbl_element.textProperty().unbind();
            lbl_element.textProperty().bind(load.currentProperty());
            lbl_count.textProperty().bind(load.countProperty());
            gp_load.setVisible(true);
            lv_accounts.setVisible(false);
            btn_newAcc.setVisible(false);
        }
        else if (Main.accountManager.getAccounts().size()>0){
            lv_accounts.setVisible(true);
            btn_newAcc.setVisible(false);
            gp_load.setVisible(false);
        } else {
            btn_newAcc.setVisible(true);
            lv_accounts.setVisible(false);
            gp_load.setVisible(false);
        }
    }

}
