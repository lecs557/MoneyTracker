package view.simple_panes;

import controller.ProfileAccountManager;
import controller.WindowManager;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.util.Callback;
import model.Main;
import model.storeclasses.Group;
import model.storeclasses.Transaction;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TransactionTable extends TableView<Transaction> {


    public TransactionTable(ArrayList<Group> groups)  {
        //SEE
        TableColumn<Transaction, LocalDate> options =new TableColumn<>("");
        options.setCellValueFactory(new PropertyValueFactory<>("localDate"));
        options.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Transaction, LocalDate> call(TableColumn<Transaction, LocalDate> transactionStringTableColumn) {
                TableCell<Transaction, LocalDate> cell = new TableCell<>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            Button b = new Button("S");
                            setGraphic(b);
                            b.setOnMouseClicked(mouseEvent ->{
                                Transaction edit = getTableRow().getItem();
                                CreateNew<Transaction> createNew = new CreateNew<>(edit, true);
                                WindowManager.openStageOf(createNew);
                            });
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        //DATUM
        TableColumn<Transaction, LocalDate> date =new TableColumn<>("Datum");
        date.setMinWidth(120);
        date.setCellValueFactory(new PropertyValueFactory<>("localDate"));
        date.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Transaction, LocalDate> call(TableColumn<Transaction, LocalDate> transactionStringTableColumn) {
                TableCell<Transaction, LocalDate> cell = new TableCell<>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            Transaction t = getTableRow().getItem();
                            if (t.getGroupId()!=0){
                                getTableRow().setStyle("-fx-background-color: "+t.findGroup(groups).getColorHex()+";" );
                            }
                            Label l = new Label(item.format(DateTimeFormatter.ofPattern("dd LLLL yyyy")));
                            setGraphic(l);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        //ZWECK
        TableColumn<Transaction, String> purpose =new TableColumn<>("purpose");
        purpose.setMinWidth(200);
        purpose.setCellValueFactory(new PropertyValueFactory<>("purpose"));
        purpose.setCellFactory(new Callback<>() {
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
                            setGraphic(l);

                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        //BETRAG
        TableColumn<Transaction, Integer> amount =new TableColumn<Transaction, Integer>("amount");
        amount.setMinWidth(80);
        amount.setCellValueFactory(new PropertyValueFactory<Transaction,Integer>("intAmount"));
        amount.setCellFactory(new Callback<>() {
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
                            Label l = new Label(new DecimalFormat("#0.00").format((double)item/100)+" €");
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


        //KONTOSTAND
        TableColumn<Transaction, Integer> balance =new TableColumn<Transaction, Integer>("Kontostand");
        balance.setMinWidth(100);
        balance.setCellValueFactory(new PropertyValueFactory<Transaction,Integer>("balance"));
        balance.setCellFactory(new Callback<>() {
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
                            Label l = new Label(new DecimalFormat("#0.00").format((double)item/100)+" €");
                            if (item > 0){
                                l.setStyle("-fx-text-fill: green;");
                            } else if (item < 0) {
                                l.setStyle("-fx-text-fill: red;");
                            }
                            l.setFont(new Font("Arial", 18));
                            setGraphic(l);// TODO mit Scenebuilder
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        getColumns().addAll(options, date, purpose, amount, balance);
    }

    public void addTransacion(Transaction transaction){
        getItems().add(transaction);
    }
}
