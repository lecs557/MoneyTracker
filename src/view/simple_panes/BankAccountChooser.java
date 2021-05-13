package view.simple_panes;

import controller.IOController;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.storeclasses.BankAccount;
import model.storeclasses.Transaction;

import java.util.ArrayList;

public class BankAccountChooser extends VBox {

    private final ChoiceBox<BankAccount> chb = new ChoiceBox<>();

    public BankAccountChooser(ArrayList<BankAccount> entrys) {

        chb.getItems().addAll(entrys);
        chb.setConverter(new StringConverter<BankAccount>() {
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

        PaneUtils.selectFirst(chb);
        getChildren().add(chb);

        Button button = new Button("Import");

        button.setOnMouseClicked(mouseEvent -> {
            Transaction.setDefaultBankAccountId(chb.getSelectionModel().getSelectedItem().getId());
            IOController.startPDFImport(PaneUtils.browsePdfFiles());
        });

        getChildren().add(button);

    }
}
