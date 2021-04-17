package view.simple_panes;

import controller.WindowManager;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import model.storeclasses.BankAccount;
import model.storeclasses.Profile;

import java.util.ArrayList;

public class BankAccountList extends VBox {

    private Button btn_add;
    public ListView<BankAccount> listView;

    public BankAccountList() {
        btn_add = new Button("Add");
        listView = new ListView<>();
        listView.setCellFactory(bankAccountListView -> {
            ListCell<BankAccount> cell = new ListCell<>() {
                @Override
                protected void updateItem(BankAccount bankAccount, boolean b) {
                    super.updateItem(bankAccount, b);
                    if (bankAccount != null) {
                        setText(bankAccount.getBankName());
                    } else {
                        setText("");
                    }
                }
            };
            return cell;
        });
        setBankAccounts(SampleClass.getSampleBankAccounts());

        btn_add.setOnMouseClicked(mouseEvent -> WindowManager.openStageOf(new CreateNew<>(new BankAccount(), false)));
        getChildren().addAll(btn_add,listView);

    }

    private void setBankAccounts(ArrayList<BankAccount> bankAccounts) {
        listView.getItems().addAll(bankAccounts);
    }

    public ListView<BankAccount> getListView() {
        return listView;
    }
}
