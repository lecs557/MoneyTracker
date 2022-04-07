package view.simple_panes;

import controller.IOController;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.storeclasses.BankAccount;
import model.storeclasses.Transaction;

import java.util.ArrayList;

public class BankAccountChooser extends VBox {

    private final ComboBox<BankAccount> cb = new ComboBox <>();

    public BankAccountChooser(ArrayList<BankAccount> entrys) {

        cb.getItems().addAll(entrys);
        cb.setConverter(new StringConverter<BankAccount>() {
            @Override
            public String toString(BankAccount bankAccount) {
                if (bankAccount != null) {
                   return bankAccount.getBankName();
                }
                return "None";
            }

            @Override
            public BankAccount fromString(String s) {
                return null;
            }
        });

        PaneUtils.selectFirst(cb);
        getChildren().add(cb);

        Button button = new Button("Import");

        button.setOnMouseClicked(mouseEvent -> {
            Transaction.setDefaultBankAccountId(cb.getSelectionModel().getSelectedItem().getId());
            IOController.getInstance().startPDFImport(PaneUtils.browsePdfFiles());
        });

        getChildren().add(button);

    }
}
