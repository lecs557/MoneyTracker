package view.customized_Panes;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import model.Account;
import model.Main;
import model.Transaction;

import java.util.Scanner;

public class TransactionTable extends TableView<Transaction> {


    public TransactionTable()  {

        setItems(Main.currentAccount.getTransactions());

        //DATUM
        TableColumn<Transaction, String> date =new TableColumn<>("Datum");
        date.setMinWidth(80);
        date.setCellValueFactory(new PropertyValueFactory<Transaction,String>("dateString"));


        //ZWECK
        TableColumn<Transaction, String> zweck =new TableColumn<>("zweck");
        zweck.setMinWidth(200);
        zweck.setCellValueFactory(new PropertyValueFactory<>("reason"));
        zweck.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Transaction, String> call(TableColumn<Transaction, String> transactionStringTableColumn) {
                TableCell<Transaction, String> cell = new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            Label l = new Label(item);
                            setGraphic(l);// TODO mit Scenebuilder
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });


        //BETRAG
        TableColumn<Transaction, Integer> betrag =new TableColumn<Transaction, Integer>("betrag");
        betrag.setMinWidth(80);
        betrag.setCellValueFactory(new PropertyValueFactory<Transaction,Integer>("betrag"));
        betrag.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Transaction, Integer> call(TableColumn<Transaction, Integer> transactionStringTableColumn) {
                TableCell<Transaction, Integer> cell = new TableCell<>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            Label l = new Label(item+"€");
                            if (item > 0){
                                l.setStyle("-fx-text-fill: green;");
                            } else if (item < 0) {
                                l.setStyle("-fx-text-fill: red;");
                            } else {
                            }
                            setGraphic(l);// TODO mit Scenebuilder
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });




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
