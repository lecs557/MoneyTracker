package view.customized_Panes;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Main;
import model.Transaction;

public class TransactionTable extends TableView<Transaction> {


    public TransactionTable()  {

        setItems(Main.currentAccount.getTransactions());

        TableColumn<Transaction, String> date =new TableColumn<>("Datum");
        date.setMinWidth(80);
        date.setCellValueFactory(new PropertyValueFactory<Transaction,String>("dateString"));
        TableColumn<Transaction, String> zweck =new TableColumn<>("zweck");
        zweck.setMinWidth(200);
        zweck.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Transaction, String> call(TableColumn<Transaction, String> transactionStringTableColumn) {
                TableCell cell = new TableCell<Transaction, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        return getItem() == null ? "" : getItem();
                    }
                };

                cell.setStyle("-fx-alignment: CENTER;");
                return cell;
            }
        });
        TableColumn<Transaction, Integer> betrag =new TableColumn<Transaction, Integer>("betrag");
        betrag.setMinWidth(80);
        betrag.setCellValueFactory(new PropertyValueFactory<Transaction,Integer>("betrag"));

        getColumns().addAll(date, zweck, betrag);
    }

    @Override
    protected double computePrefHeight(double width){
        return 100+Main.currentAccount.getTransactions().size()*50;
    }

    @Override
    protected double computeMaxHeight(double width){
        return 500;
    }
}
