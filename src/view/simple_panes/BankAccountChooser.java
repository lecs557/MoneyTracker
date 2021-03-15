package view.simple_panes;

import controller.IOController;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import model.Main;
import model.storeclasses.BankAccount;
import model.storeclasses.Transaction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BankAccountChooser extends VBox {

    private ArrayList<BankAccount>  entrys;
    private ChoiceBox<BankAccount> chb = new ChoiceBox<>();

    public BankAccountChooser(ArrayList<BankAccount> entrys) {
        this.entrys = entrys;

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

        getChildren().add(chb);

        Button button = new Button("Import");

        button.setOnMouseClicked(mouseEvent -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("PDF auswählen");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDFFiles", "*.pdf"));
            List<File> files = chooser.showOpenMultipleDialog(Main.primaryStage);
            Transaction.setDefaultBankAccountId(chb.getSelectionModel().getSelectedItem().getId());
            IOController.startPDFImport(files);
        });

        getChildren().add(button);

    }
}
