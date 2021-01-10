package view.simple_panes;

import controller.ContentController;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import model.Main;
import model.storeclasses.BankAccount;
import model.storeclasses.StoreClass;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ImportForBankaccount extends VBox {

    private ArrayList<BankAccount>  entrys;
    private ChoiceBox<BankAccount> chb = new ChoiceBox<>();

    public ImportForBankaccount(ArrayList<BankAccount> entrys) {
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
            ContentController.setBankAccount(chb.getSelectionModel().getSelectedItem());
            Main.ioController.startPDFImport(files);
        });

        getChildren().add(button);

    }
}
